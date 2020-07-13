package com.github.javalab.javaiaas.models;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Application {

    @Id
    private Long id;

    private String gitUrl;

    //TODO
    private String ownerName;
}
