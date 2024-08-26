package com.arkmon.autocicd.events;

import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 集成测试模块
 *
 * @author X.J
 * @date 2021/2/25
 */
@Getter
public class NotificationEvent extends ApplicationEvent {

    private MonitorQueueItemEntity monitorQueueItem;
    private String notifyWay;
    private String msg;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public NotificationEvent(Object source) {
        super(source);
    }

    public NotificationEvent(Object source, MonitorQueueItemEntity m) {
        super(source);
        this.monitorQueueItem = m;
    }

    public NotificationEvent(Object source, MonitorQueueItemEntity m, String notifyWay) {
        super(source);
        this.monitorQueueItem = m;
        this.notifyWay = notifyWay;
    }

    public NotificationEvent(Object source, MonitorQueueItemEntity monitorQueueItem, String notifyWay, String msg) {
        super(source);
        this.monitorQueueItem = monitorQueueItem;
        this.notifyWay = notifyWay;
        this.msg = msg;
    }
}
