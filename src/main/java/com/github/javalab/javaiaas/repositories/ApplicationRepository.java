package com.github.javalab.javaiaas.repositories;

import com.github.javalab.javaiaas.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByGitUrl(String gitUrl);

    @Transactional
    List<Application> findAllByUserId(@Param("id") Long id);
}
