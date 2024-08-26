package com.arkmon.autocicd.domains.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PlatformManagementDownloadReqDTO {
    @Past(message = "startDate 不能为将来时")
    //对于转换前端传过来的时间，@JsonFormat只适合 Content-Type 为application/json的请求，如果是表单请求，请采用@DateTimeFormat。
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private String start;
    private String end;
    private Boolean useStringDate;
    private String project;
    private String service;
    private String userId;
    private String environment;
}
