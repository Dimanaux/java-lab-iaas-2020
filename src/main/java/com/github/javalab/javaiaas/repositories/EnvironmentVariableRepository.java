package com.github.javalab.javaiaas.repositories;

import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.models.EnvironmentVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentVariableRepository extends JpaRepository<EnvironmentVariable, Long> {

    @Query("SELECT v FROM EnvironmentVariable v WHERE v.name = ?1 and v.application = ?2")
    EnvironmentVariable findByName(String name, Application application);

    @Query("SELECT v FROM EnvironmentVariable v where v.application = ?1")
    List<EnvironmentVariable> findAllByApplication(Application application);
}
