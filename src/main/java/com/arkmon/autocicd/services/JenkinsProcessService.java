package com.arkmon.autocicd.services;

import ReturnEntities.ReturnData;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;

/**
 * @author X.J
 * @date 2021/2/7
 */
public interface JenkinsProcessService {
    ReturnData build(JenkinsProcessBuildReqDTO jenkinsProcessBuildReqDTO);
}
