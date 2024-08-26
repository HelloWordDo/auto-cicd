package com.arkmon.autocicd.configs;

import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author X.J
 * @date 2021/2/25
 */
@Configuration
public class QueueMonitorConfig {

    @Bean
    public Queue<MonitorQueueItemEntity> monitorBuildQueue() {
        return new LinkedList<>();
    }

    @Bean
    public Queue<MonitorQueueItemEntity> monitorTagQueue() {
        return new LinkedList<>();
    }

    @Bean
    public Map<String, JenkinsProcessBuildReqDTO> monitorWaitingMap() {
        return Maps.newConcurrentMap();
    }
}
