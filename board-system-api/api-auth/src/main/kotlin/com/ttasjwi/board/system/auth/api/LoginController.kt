package com.ttasjwi.board.system.auth.api

import com.ttasjwi.board.system.auth.api.LoginController.Companion.TOKEN_TYPE
import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResult
import com.ttasjwi.board.system.auth.application.usecase.LoginUseCase
import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.message.MessageResolver
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class LoginController(
    private val useCase: LoginUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    companion object {
        internal const val TOKEN_TYPE = "Bearer"
    }

    @PostMapping("/api/v1/auth/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<SuccessResponse<LoginResponse>> {
        // 유즈케이스에 요청 처리를 위임
        val result = useCase.login(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 200 상태코드와 함께 HTTP 응답
        return ResponseEntity.ok(response)
    }

    private fun makeResponse(result: LoginResult): SuccessResponse<LoginResponse> {
        val code = "Login.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale),
            data = LoginResponse(
                loginResult = LoginResponse.LoginResult(
                    accessToken = result.accessToken,
                    accessTokenExpiresAt = result.accessTokenExpiresAt,
                    refreshToken = result.refreshToken,
                    refreshTokenExpiresAt = result.refreshTokenExpiresAt,
                )
            )
        )
    }
}

data class LoginResponse(
    val loginResult: LoginResult,
) {

    data class LoginResult(
        val accessToken: String,
        val accessTokenExpiresAt: ZonedDateTime,
        val tokenType: String = TOKEN_TYPE,
        val refreshToken: String,
        val refreshTokenExpiresAt: ZonedDateTime,
    )
}
