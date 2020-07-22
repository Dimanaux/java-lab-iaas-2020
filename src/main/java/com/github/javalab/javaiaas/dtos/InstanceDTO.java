package com.github.javalab.javaiaas.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstanceDTO {
    private String gitUrl;
    private Long userId;
    private Long appId;
    private String instanceName;
    private String instanceUrl;
}
