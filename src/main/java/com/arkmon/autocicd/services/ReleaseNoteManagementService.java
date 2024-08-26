package com.arkmon.autocicd.services;

import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;

public interface ReleaseNoteManagementService {

    void save(MonitorQueueItemEntity m);
}
