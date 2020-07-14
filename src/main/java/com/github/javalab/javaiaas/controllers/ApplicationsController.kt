package com.github.javalab.javaiaas.controllers

import com.github.javalab.javaiaas.models.Application
import com.github.javalab.javaiaas.repositories.ApplicationRepository
import com.github.javalab.javaiaas.security.details.UserDetailsImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/applications")
class ApplicationsController(
        var applicationsRepository: ApplicationRepository?
) {
    @GetMapping
    fun index(current: Authentication): ResponseEntity<MutableList<Application>> {
        return ResponseEntity.ok(current.user.applications)
    }

    @PostMapping
    fun create(current: Authentication, @RequestBody application: Application): ResponseEntity<Application> {
        application.user = current.user
        applicationsRepository?.save(application)
        return ResponseEntity.ok(application)
    }

    private val Authentication.user
        get() = (this.principal as UserDetailsImpl).user
}
