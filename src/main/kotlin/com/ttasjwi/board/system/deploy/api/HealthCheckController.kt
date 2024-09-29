package com.ttasjwi.board.system.deploy.api

import com.ttasjwi.board.system.deploy.config.DeployProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    private val deployProperties: DeployProperties,
){

    @GetMapping("/api/v1/deploy/health-check")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                deployProperties.profile
            )
    }
}
