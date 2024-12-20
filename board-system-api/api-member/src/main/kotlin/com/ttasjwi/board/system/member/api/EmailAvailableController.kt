package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableResult
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailAvailableController(
    private val useCase: EmailAvailableUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    @GetMapping("/api/v1/members/email-available")
    fun checkEmailAvailable(request: EmailAvailableRequest): ResponseEntity<SuccessResponse<EmailAvailableResponse>> {
        // 애플리케이션 서비스에 요청 처리를 위임
        val result = useCase.checkEmailAvailable(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 200 상태코드와 함께 HTTP 응답
        return ResponseEntity.ok(response)
    }

    private fun makeResponse(result: EmailAvailableResult): SuccessResponse<EmailAvailableResponse> {
        val code = "EmailAvailableCheck.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale, listOf("$.data.emailAvailable")),
            data = EmailAvailableResponse(
                emailAvailable = EmailAvailableResponse.EmailAvailable(
                    email = result.email,
                    isAvailable = result.isAvailable,
                    reasonCode = result.reasonCode,
                    message = messageResolver.resolve("${result.reasonCode}.message", locale),
                    description = messageResolver.resolve("${result.reasonCode}.description", locale),
                )
            )
        )
    }
}

data class EmailAvailableResponse(
    val emailAvailable: EmailAvailable,
) {
    data class EmailAvailable(
        val email: String,
        val isAvailable: Boolean,
        val reasonCode: String,
        val message: String,
        val description: String,
    )
}
