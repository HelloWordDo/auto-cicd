package com.arkmon.autocicd.events;

import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReleaseNoteManagementEvent extends ApplicationEvent {

    private MonitorQueueItemEntity monitorQueueItem;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ReleaseNoteManagementEvent(Object source) {
        super(source);
    }

    public ReleaseNoteManagementEvent(Object source, MonitorQueueItemEntity monitorQueueItem) {
        super(source);
        this.monitorQueueItem = monitorQueueItem;
    }
}
