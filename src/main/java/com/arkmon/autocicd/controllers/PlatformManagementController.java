package com.arkmon.autocicd.controllers;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import com.arkmon.autocicd.annotations.AccessCheck;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.domains.dto.request.PlatformManagementDownloadReqDTO;
import com.arkmon.autocicd.services.JenkinsProcessService;
import com.arkmon.autocicd.services.PlatformManagementService;
import enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

/**
 * 管理后台接口
 *
 * @author X.J
 * @date 2021/2/7
 */
@RestController
@RequestMapping("/management")
@Validated
@Slf4j
public class PlatformManagementController {

    @Resource
    PlatformManagementService platformManagementService;

    /**
     * 下载release note为excel格式
     *
     * @param
     * @return ReturnData
     */
    @PostMapping("/download")
    public ResponseEntity<ReturnData> download(@Valid @RequestBody PlatformManagementDownloadReqDTO pmdReq, HttpServletResponse response) {
        return Optional.ofNullable(platformManagementService.download(pmdReq, response))
                .map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .orElseThrow(() -> new MAException("error.autocide.download", ErrorEnum.BUSINESS_ERROR.getMsgCode()));
    }

    @GetMapping("/download")
    public ResponseEntity<ReturnData> download(@RequestParam String startDate, @RequestParam String endDate, HttpServletResponse response) {
        return Optional.ofNullable(platformManagementService.download(startDate, endDate, response))
                .map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .orElseThrow(() -> new MAException("error.autocide.download", ErrorEnum.BUSINESS_ERROR.getMsgCode()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<ReturnData> refresh() {
        return Optional.ofNullable(platformManagementService.refresh())
                .map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .orElseThrow(() -> new MAException("refresh失败"));
    }

    @GetMapping("/projectData")
    public ResponseEntity<ReturnData> projectData() {
        return Optional.ofNullable(platformManagementService.projectData())
                .map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .orElseThrow(() -> new MAException("获取项目信息失败"));
    }

    @GetMapping("/taskHistory")
    public ResponseEntity<ReturnData> taskHistory() {
        return Optional.ofNullable(platformManagementService.taskHistory())
                .map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .orElseThrow(() -> new MAException("获取部署任务历史失败"));
    }

    @GetMapping("/waitingTasks")
    public ResponseEntity<ReturnData> waitingTasks() {
        return Optional.ofNullable(platformManagementService.waitingTasks())
                .map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .orElseThrow(() -> new MAException("获取等待部署任务队列失败"));
    }
}
