package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartResult
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class EmailVerificationStartController(
    private val useCase: EmailVerificationStartUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    @PostMapping("/api/v1/members/email-verification/start")
    fun startEmailVerification(@RequestBody request: EmailVerificationStartRequest): ResponseEntity<SuccessResponse<EmailVerificationStartResponse>> {
        // 애플리케이션 서비스에 요청 처리를 위임
        val result = useCase.startEmailVerification(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 200 상태코드와 함께 HTTP 응답
        return ResponseEntity.ok(response)
    }

    private fun makeResponse(result: EmailVerificationStartResult): SuccessResponse<EmailVerificationStartResponse> {
        val code = "EmailVerificationStart.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolveMessage(code, locale),
            description = messageResolver.resolveDescription(code, listOf("$.data.emailVerificationStartResult"), locale),
            data = EmailVerificationStartResponse(
                EmailVerificationStartResponse.EmailVerificationStartResult(
                    email = result.email,
                    codeExpiresAt = result.codeExpiresAt
                )
            )
        )
    }
}

data class EmailVerificationStartResponse(
    val emailVerificationStartResult: EmailVerificationStartResult
) {

    data class EmailVerificationStartResult(
        val email: String,
        val codeExpiresAt: ZonedDateTime,
    )
}
