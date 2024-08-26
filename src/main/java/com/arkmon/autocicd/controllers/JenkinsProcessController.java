package com.arkmon.autocicd.controllers;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import com.arkmon.autocicd.annotations.AccessCheck;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.services.JenkinsProcessService;
import enums.ErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * 触发jenkins各个环境部署的接口
 *
 * @author X.J
 * @date 2021/2/7
 */
@RestController
@RequestMapping("/jenkins")
@Validated
public class JenkinsProcessController {

    @Resource
    JenkinsProcessService jenkinsProcessService;

    /**
     * 触发jenkins部署
     * @param jenkinsProcessBuildReqDTO
     * @return ReturnData
     */
    @PostMapping("/build")
    @AccessCheck
    public ResponseEntity<ReturnData> build(@Valid @RequestBody JenkinsProcessBuildReqDTO jenkinsProcessBuildReqDTO) {
        return Optional.ofNullable(jenkinsProcessService.build(jenkinsProcessBuildReqDTO))
                .map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .orElseThrow(() -> new MAException("error.autocide.build", ErrorEnum.BUSINESS_ERROR.getMsgCode()));
    }
}
