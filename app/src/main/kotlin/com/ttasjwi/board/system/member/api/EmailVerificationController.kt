package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.LocaleManager
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationResult
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class EmailVerificationController(
    private val useCase: EmailVerificationUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    @PostMapping("/api/v1/members/email-verification")
    fun emailVerification(@RequestBody request: EmailVerificationRequest): ResponseEntity<SuccessResponse<EmailVerificationResponse>> {
        // 애플리케이션 서비스에 요청 처리를 위임
        val result = useCase.emailVerification(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 200 상태코드와 함께 HTTP 응답
        return ResponseEntity.ok(response)
    }

    private fun makeResponse(result: EmailVerificationResult): SuccessResponse<EmailVerificationResponse> {
        val code = "EmailVerification.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale),
            data = EmailVerificationResponse(
                verificationResult = EmailVerificationResponse.VerificationResult(
                    email = result.email,
                    verificationExpiresAt = result.verificationExpiresAt,
                )
            )
        )
    }
}

data class EmailVerificationResponse(
    val verificationResult: VerificationResult
) {

    data class VerificationResult(
        val email: String,
        val verificationExpiresAt: ZonedDateTime,
    )
}
