package com.arkmon.autocicd.domains.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ProjectDataRspDTO {
    private Set<String> projects;
    private Set<String> services;
    private Set<String> environments;
    private List<String> releases;
    private String release;
}
