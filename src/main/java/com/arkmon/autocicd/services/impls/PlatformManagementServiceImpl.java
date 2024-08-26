package com.arkmon.autocicd.services.impls;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import cn.hutool.core.collection.CollectionUtil;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.domains.dto.request.PlatformManagementDownloadReqDTO;
import com.arkmon.autocicd.domains.dto.response.JenkinsProcessHistoryRspDTO;
import com.arkmon.autocicd.domains.dto.response.ProjectDataRspDTO;
import com.arkmon.autocicd.domains.model.*;
import com.arkmon.autocicd.enums.ReturnParameterEnum;
import com.arkmon.autocicd.mappers.*;
import com.arkmon.autocicd.services.PlatformManagementService;
import com.arkmon.autocicd.utils.ExcelUtil;
import com.arkmon.autocicd.utils.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlatformManagementServiceImpl implements PlatformManagementService {
    @Resource
    private UserInfoMapper uim;
    @Resource
    private UserAccessMapper uam;
    @Resource
    private JenkinsServiceUrlMapper jsum;
    @Resource
    private JenkinsTagUrlMapper jtum;
    @Resource
    private ReleaseNoteMapper rnm;
    @Resource
    private DeployDeniedWindowMapper ddwm;
    @Resource
    private JenkinsProcessHistoryMapper jphm;
    @Resource
    private ReleaseVersionMapper rvm;

    @Resource(name = "obtainUserInfo")
    private Map<String, UserInfo> userInfo;
    @Resource(name = "obtainJenkinsServiceUrl")
    private Map<String, JenkinsServiceUrl> jsu;
    @Resource(name = "obtainJenkinsTagUrl")
    private Map<String, JenkinsTagUrl> jtu;
    @Resource(name = "obtainDeniedWindow")
    private Map<String, DeployDeniedWindow> odw;
    @Resource(name = "obtainUserAccess")
    private Map<String, UserAccess> oua;
    @Resource(name = "obtainReleaseVersion")
    private ReleaseVersion orv;
    @Resource(name = "monitorWaitingMap")
    private Map<String, JenkinsProcessBuildReqDTO> monitorWaitingMap;

    private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public ReturnData download(String start, String end, HttpServletResponse response) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate startLocalDate = LocalDate.parse(start, dtf);
        LocalDate endLocalDate = LocalDate.parse(end, dtf);
        if (startLocalDate.isAfter(endLocalDate)) {
            return new ReturnData(ReturnParameterEnum.FAILED.getCode(), "startDate 不能晚于 endDate");
        }
        PlatformManagementDownloadReqDTO rmdReq = new PlatformManagementDownloadReqDTO();
        rmdReq.setStart(start);
        rmdReq.setEnd(end);
        rmdReq.setUseStringDate(true);
        return download(rmdReq, response);
    }

    @Override
    public ReturnData projectData() {
        Set<String> projects = new TreeSet<>();
        Set<String> services = new TreeSet<>();
        Set<String> envvironments = new TreeSet<>();
        jsu.forEach((k, v) -> {
            projects.add(v.getProject());
            services.add(v.getService());
            envvironments.add(v.getEnvironment());
        });
        String optionals = orv.getReleaseVersionOptional().replace(" ", "").trim();
        List<String> releases = new ArrayList<>();
        try {
            releases = Arrays.asList(optionals.split("::"));
        } catch (Exception e){
            log.error("获取releases失败");
        }
        ProjectDataRspDTO rspDTO = ProjectDataRspDTO.builder()
                .environments(envvironments)
                .projects(projects)
                .services(services)
                .release(orv.getReleaseVersion().replace(" ","").trim())
                .releases(releases)
                .build();
        return new ReturnData(rspDTO);
    }

    @Override
    public ReturnData taskHistory() {
        List<JenkinsProcessHistory> histories = jphm.selectList(new QueryWrapper<JenkinsProcessHistory>()
                .orderByDesc("id").last("limit 10"));
        List<JenkinsProcessHistoryRspDTO> rspDTOList = new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.SIMPLIFIED_CHINESE);
        histories.forEach(item -> {
            JenkinsProcessHistoryRspDTO rspDTO = new JenkinsProcessHistoryRspDTO();
            BeanUtils.copyProperties(item, rspDTO);
            rspDTO.setCreationDate(sdf.format(item.getCreationDate()));
            rspDTOList.add(rspDTO);
        });
        return new ReturnData(rspDTOList);
    }

    @Override
    public ReturnData waitingTasks() {
        ArrayList<Object> list = new ArrayList<>();
        monitorWaitingMap.forEach((k,v)->{
            list.add(v);
        });
        return new ReturnData(list);
    }

    @Override
    public ReturnData download(PlatformManagementDownloadReqDTO rmdReq, HttpServletResponse response) {
        String start = rmdReq.getStart();
        String end = rmdReq.getEnd();
        if (rmdReq.getUseStringDate() == null || !rmdReq.getUseStringDate()) {
            Boolean dateCheckPass = Optional.ofNullable(rmdReq).map(item -> item.getStartDate().before(item.getEndDate()) || item.getStartDate().equals(item.getEndDate())).orElse(false);
            if (!dateCheckPass) {
                return new ReturnData(ReturnParameterEnum.FAILED.getCode(), "startDate 不能晚于 endDate");
            }
            start = DateFormatUtils.format(rmdReq.getStartDate(), DATE_FORMAT);
            end = DateFormatUtils.format(rmdReq.getEndDate(), DATE_FORMAT);
        }
        List<ReleaseNote> releaseNotes = rnm.selectByDate(start, end);
        if (CollectionUtil.isEmpty(releaseNotes)) {
            return new ReturnData(ReturnParameterEnum.SUCCESS.getCode(), "没有找到相应的release note记录");
        }
        String fileName = String.format("%s.xls", System.currentTimeMillis());
        List<Map<String, Object>> collect = releaseNotes.stream().map(MapUtil::obj2Map).collect(Collectors.toList());
        ExcelUtil.exportExcel(collect, fileName);
        String filePath = "";
        File file = new File(filePath + fileName);
        if (file != null) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("{}", e);
                return new ReturnData(ReturnParameterEnum.FAILED.getCode(), "下载失败");
            }
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                log.info("Download  successfully!");
                return new ReturnData(ReturnParameterEnum.SUCCESS.getCode(), "下载成功");

            } catch (Exception e) {
                log.info("Download  failed! {}", e);
                return new ReturnData(ReturnParameterEnum.FAILED.getCode(), "下载失败");
            } finally {
                if (!FileSystemUtils.deleteRecursively(file)) {
                    log.error("文件{}删除失败", file.getName());
                }
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ReturnData(ReturnParameterEnum.SUCCESS.getCode(), ReturnParameterEnum.SUCCESS.getName());
    }

    @Override
    public ReturnData refresh() {
        List<UserInfo> userInfos = uim.selectList(null);
        Optional.ofNullable(userInfos).orElseThrow(() -> new MAException("用户信息表为空，请修正"));
        Map<String, UserInfo> collect = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, Function.identity()));
        userInfo.clear();
        userInfo.putAll(collect);

        List<JenkinsServiceUrl> jenkinsServiceUrls = jsum.selectList(null);
        Optional.ofNullable(jenkinsServiceUrls).orElseThrow(() -> new MAException("用户信息表为空，请修正"));
        Map<String, JenkinsServiceUrl> collect2 = jenkinsServiceUrls.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s-%s", k.getProject(), k.getEnvironment(), k.getService()),
                Function.identity()));
        log.info("userInfo:{}" + userInfo.toString());
        jsu.clear();
        jsu.putAll(collect2);

        List<JenkinsTagUrl> jenkinsTagUrls = jtum.selectList(null);
        Optional.ofNullable(jenkinsTagUrls).orElseThrow(() -> new MAException("用户信息表为空，请修正"));
        Map<String, JenkinsTagUrl> collect3 = jenkinsTagUrls.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s", k.getProject(), k.getEnvironment()),
                Function.identity()));
        jtu.clear();
        jtu.putAll(collect3);

        List<DeployDeniedWindow> deployDeniedWindows = ddwm.selectList(null);
        Optional.ofNullable(deployDeniedWindows).orElseThrow(() -> new MAException("部署denied表为空，请修正"));
        Map<String, DeployDeniedWindow> collect4 = deployDeniedWindows.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s", k.getProjectName(), k.getEnvironment()),
                Function.identity()));
        odw.clear();
        odw.putAll(collect4);

        List<UserAccess> userAccesses = uam.selectList(null);
        Optional.ofNullable(userAccesses).orElseThrow(() -> new MAException("用户权限表为空，请修正"));
        Map<String, UserAccess> collect5 = userAccesses.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s-%s-%s", k.getUserId(), k.getProject(), k.getServiceName(), k.getEnvironment()),
                Function.identity()));
        oua.clear();
        oua.putAll(collect5);

        List<ReleaseVersion> releaseVersions = rvm.selectList(null);
        Optional.ofNullable(releaseVersions).orElseThrow(() -> new MAException("release version为空，请修正"));
        BeanUtils.copyProperties(releaseVersions.get(0), orv);

        return new ReturnData(ReturnParameterEnum.SUCCESS.getCode(), ReturnParameterEnum.SUCCESS.getName());
    }

}
