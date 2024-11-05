package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableResult
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UsernameAvailableController(
    private val useCase: UsernameAvailableUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    @GetMapping("/api/v1/members/username-available")
    fun checkUsernameAvailable(request: UsernameAvailableRequest): ResponseEntity<SuccessResponse<UsernameAvailableResponse>> {
        // 애플리케이션 서비스에 요청 처리를 위임
        val result = useCase.checkUsernameAvailable(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 200 상태코드와 함께 HTTP 응답
        return ResponseEntity.ok(response)
    }

    private fun makeResponse(result: UsernameAvailableResult): SuccessResponse<UsernameAvailableResponse> {
        val code = "UsernameAvailableCheck.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolveMessage(code, locale),
            description = messageResolver.resolveDescription(code, listOf("$.data.usernameAvailable"), locale),
            data = UsernameAvailableResponse(
                usernameAvailable = UsernameAvailableResponse.UsernameAvailable(
                    username = result.username,
                    isAvailable = result.isAvailable,
                    reasonCode = result.reasonCode,
                    message = messageResolver.resolveMessage(result.reasonCode, locale),
                    description = messageResolver.resolveDescription(result.reasonCode, emptyList(), locale),
                )
            )
        )
    }
}

data class UsernameAvailableResponse(
    val usernameAvailable: UsernameAvailable
) {
    data class UsernameAvailable(
        val username: String,
        val isAvailable: Boolean,
        val reasonCode: String,
        val message: String,
        val description: String
    )
}
