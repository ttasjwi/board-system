package com.ttasjwi.board.system.deploy.api

import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.LocaleManager
import com.ttasjwi.board.system.common.message.MessageResolver
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,

    @Value("\${spring.profiles.active}")
    private val serverProfile: String,
) {

    @GetMapping("/api/v1/deploy/health-check")
    fun healthCheck(): ResponseEntity<SuccessResponse<HealthCheckResponse>> {
        val code = "HealthCheck.Success"
        val locale = localeManager.getCurrentLocale()
        val args = listOf("$.data.profile")
        val response = SuccessResponse(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale, args),
            data = HealthCheckResponse(
                profile = serverProfile
            )
        )
        return ResponseEntity.ok(response)
    }
}

data class HealthCheckResponse (
    val profile: String,
)
