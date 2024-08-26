package com.arkmon.autocicd.services;

import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;

/**
 * 通知模块接口
 *
 * @author X.J
 * @date 2021/2/25
 */
public interface NotificationService {
    void notifyByEmail(MonitorQueueItemEntity m, String msg);

    void notifyByDingTalk(MonitorQueueItemEntity m, String msg);
}
