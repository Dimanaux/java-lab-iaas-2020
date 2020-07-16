package com.github.javalab.javaiaas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enviroment_variables")
public class EnvironmentVariable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String value;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @JoinColumn(name = "application_id")
    private Application application;
}
