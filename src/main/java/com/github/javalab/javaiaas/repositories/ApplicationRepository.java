package com.github.javalab.javaiaas.repositories;

import com.github.javalab.javaiaas.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByGitUrl(String gitUrl);
}
