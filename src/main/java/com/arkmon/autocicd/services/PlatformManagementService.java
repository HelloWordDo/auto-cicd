package com.arkmon.autocicd.services;

import ReturnEntities.ReturnData;
import com.arkmon.autocicd.domains.dto.request.PlatformManagementDownloadReqDTO;

import javax.servlet.http.HttpServletResponse;

public interface PlatformManagementService {

    ReturnData download(PlatformManagementDownloadReqDTO pmdReq, HttpServletResponse response);

    ReturnData refresh();

    ReturnData download(String startDate, String endDate, HttpServletResponse response);

    ReturnData projectData();

    ReturnData taskHistory();

    ReturnData waitingTasks();
}
