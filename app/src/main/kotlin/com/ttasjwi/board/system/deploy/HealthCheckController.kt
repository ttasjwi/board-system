package com.ttasjwi.board.system.deploy

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    @Value("\${spring.profiles.active}")
    private val serverProfile: String,
) {

    @GetMapping("/api/v1/deploy/health-check")
    fun healthCheck(): ResponseEntity<HealthCheckResponse> {
        return ResponseEntity.ok(HealthCheckResponse(serverProfile))
    }
}

data class HealthCheckResponse(
    val profile: String,
)
