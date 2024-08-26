package com.arkmon.autocicd.domains.entity;

import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import lombok.Builder;
import lombok.Data;


/**
 * @author X.J
 * @date 2021/2/25
 */
@Data
@Builder
public class MonitorQueueItemEntity {
    private JenkinsProcessBuildReqDTO jenkinsProcessBuildReqDTO;
    private String originalEnvironment;
    private Long ts;
    private Integer lastBuild;
    private String tagBaseUrl;
    private String sonarqubeUrl;
    private String commitId;
    private String taskId;
    private String integratedTestFile;
}
