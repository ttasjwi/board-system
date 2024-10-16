package com.ttasjwi.board.system.deploy.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.deploy.config.DeployProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    private val messageResolver: MessageResolver,
    private val deployProperties: DeployProperties,
) {

    @GetMapping("/api/v1/deploy/health-check")
    fun healthCheck(): ResponseEntity<SuccessResponse<HealthCheckResponse>> {
        val code = "HealthCheck.Success"
        val args = listOf("$.data.profile")
        val response = SuccessResponse(
            code = code,
            message = messageResolver.resolveMessage(code),
            description = messageResolver.resolveDescription(code, args),
            data = HealthCheckResponse(
                profile = deployProperties.profile
            )
        )
        return ResponseEntity.ok(response)
    }
}
