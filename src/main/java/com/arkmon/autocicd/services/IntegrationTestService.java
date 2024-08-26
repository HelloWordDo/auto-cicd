package com.arkmon.autocicd.services;

import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;

/**
 * 集成测试模块服务接口
 *
 * @author X.J
 * @date 2021/2/25
 */
public interface IntegrationTestService {
    void start(MonitorQueueItemEntity m);
}
