package com.arkmon.autocicd.events;

import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 集成测试模块
 *
 * @author X.J
 * @date 2021/2/25
 */
public class JenkinsProcessEvent extends ApplicationEvent {

    private MonitorQueueItemEntity m;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public JenkinsProcessEvent(Object source) {
        super(source);
    }

    public JenkinsProcessEvent(Object source, MonitorQueueItemEntity m) {
        super(source);
        this.m = m;
    }

    public MonitorQueueItemEntity getMonitorQueueItem() {
        return this.m;
    }
}
