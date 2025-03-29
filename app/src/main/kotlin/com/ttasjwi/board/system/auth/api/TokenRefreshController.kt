package com.ttasjwi.board.system.auth.api

import com.ttasjwi.board.system.auth.api.TokenRefreshController.Companion.TOKEN_TYPE
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshResult
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshUseCase
import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.LocaleManager
import com.ttasjwi.board.system.common.message.MessageResolver
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class TokenRefreshController(
    private val useCase: TokenRefreshUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    companion object {
        internal const val TOKEN_TYPE = "Bearer"
    }

    @PostMapping("/api/v1/auth/token-refresh")
    fun login(@RequestBody request: TokenRefreshRequest): ResponseEntity<SuccessResponse<TokenRefreshResponse>> {
        // 유즈케이스에 요청 처리를 위임
        val result = useCase.tokenRefresh(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 200 상태코드와 함께 HTTP 응답
        return ResponseEntity.ok(response)
    }

    private fun makeResponse(result: TokenRefreshResult): SuccessResponse<TokenRefreshResponse> {
        val code = "TokenRefresh.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale),
            data = TokenRefreshResponse(
                tokenRefreshResult = TokenRefreshResponse.TokenRefreshResult(
                    accessToken = result.accessToken,
                    accessTokenExpiresAt = result.accessTokenExpiresAt,
                    refreshToken = result.refreshToken,
                    refreshTokenExpiresAt = result.refreshTokenExpiresAt,
                    refreshTokenRefreshed = result.refreshTokenRefreshed
                )
            )
        )
    }
}

data class TokenRefreshResponse(
    val tokenRefreshResult: TokenRefreshResult,
) {

    data class TokenRefreshResult(
        val accessToken: String,
        val accessTokenExpiresAt: ZonedDateTime,
        val tokenType: String = TOKEN_TYPE,
        val refreshToken: String,
        val refreshTokenExpiresAt: ZonedDateTime,
        val refreshTokenRefreshed: Boolean,
    )
}
