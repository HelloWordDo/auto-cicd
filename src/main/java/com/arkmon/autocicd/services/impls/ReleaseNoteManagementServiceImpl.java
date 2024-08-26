package com.arkmon.autocicd.services.impls;

import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import com.arkmon.autocicd.domains.model.ReleaseNote;
import com.arkmon.autocicd.events.ReleaseNoteManagementEvent;
import com.arkmon.autocicd.mappers.ReleaseNoteMapper;
import com.arkmon.autocicd.services.ReleaseNoteManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class ReleaseNoteManagementServiceImpl implements ReleaseNoteManagementService, ApplicationEventPublisherAware, ApplicationListener<ReleaseNoteManagementEvent> {

    private ApplicationEventPublisher aep;

    @Resource
    private ReleaseNoteMapper rnm;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher aep) {
        this.aep = aep;
    }

    @Override
    public void onApplicationEvent(ReleaseNoteManagementEvent rme) {
        ReleaseNote rn = ReleaseNote.builder()
                .commitId(rme.getMonitorQueueItem().getCommitId())
                .environment(rme.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment())
                .project(rme.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getProjectName())
                .reason(rme.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getReason())
                .service(rme.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getServiceName())
                .tag(rme.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getBranch())
                .userId(rme.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getApplicant())
                .jiraId(rme.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getJiraId())
                .build();
        if (rnm.insert(rn) != 1) {
            log.error("release note 保存失败：{}", rn.toString());
        }
    }

    @Override
    public void save(MonitorQueueItemEntity m) {
        log.info("请求保存release note");
        aep.publishEvent(new ReleaseNoteManagementEvent(this, m));
    }
}
