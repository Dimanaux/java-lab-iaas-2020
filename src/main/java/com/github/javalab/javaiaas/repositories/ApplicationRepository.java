package com.github.javalab.javaiaas.repositories;

import com.github.javalab.javaiaas.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
