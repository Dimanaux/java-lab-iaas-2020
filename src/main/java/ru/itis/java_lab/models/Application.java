package ru.itis.java_lab.models;


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
