package com.ttasjwi.board.system.deploy.api

import com.ttasjwi.board.system.deploy.config.ServerProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DeployController(
    private val serverProperties: ServerProperties,
){

    @GetMapping("/api/v1/deploy/health-check")
    fun healthCheck(): ResponseEntity<HealthCheckResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                HealthCheckResponse(
                    serverName = serverProperties.name,
                    serverAddress = serverProperties.address,
                    serverPort = serverProperties.port,
                    serverEnv = serverProperties.env,
                )
            )
    }

    @GetMapping("/api/v1/deploy/env")
    fun env(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                serverProperties.env
            )
    }
}
