package ru.itis.java_lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.java_lab.models.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
