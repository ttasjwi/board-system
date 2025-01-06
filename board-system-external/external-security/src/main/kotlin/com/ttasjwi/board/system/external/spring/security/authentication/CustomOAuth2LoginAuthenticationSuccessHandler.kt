package com.ttasjwi.board.system.external.spring.security.authentication

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResult
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginUseCase
import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.external.spring.security.oauth2.SocialServiceUserPrincipal
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.time.ZonedDateTime

class CustomOAuth2LoginAuthenticationSuccessHandler(
    private val useCase: SocialLoginUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) : AuthenticationSuccessHandler {

    companion object {
        internal const val TOKEN_TYPE = "Bearer"
        private val objectMapper = jacksonObjectMapper()
            .registerModules(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val socialServiceUserPrincipal = authentication.principal as SocialServiceUserPrincipal

        val socialLoginRequest = SocialLoginRequest(
            socialServiceName = socialServiceUserPrincipal.socialServiceName(),
            socialServiceUserId = socialServiceUserPrincipal.socialServiceUserId(),
            email = socialServiceUserPrincipal.email()
        )

        val result = useCase.socialLogin(socialLoginRequest)

        // Http 응답 작성
        writeResponse(result, response)
    }

    private fun writeResponse(
        result: SocialLoginResult,
        response: HttpServletResponse
    ) {

        val socialLoginResponse = makeResponse(result)

        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(socialLoginResponse))
    }

    private fun makeResponse(result: SocialLoginResult): SuccessResponse<SocialLoginResponse> {
        val code = "SocialLogin.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale),
            data = SocialLoginResponse(
                accessToken = SocialLoginResponse.AccessToken(
                    tokenValue = result.accessToken,
                    tokenType = TOKEN_TYPE,
                    tokenExpiresAt = result.accessTokenExpiresAt
                ),
                refreshToken = SocialLoginResponse.RefreshToken(
                    tokenValue = result.refreshToken,
                    tokenExpiresAt = result.refreshTokenExpiresAt
                ),
                memberCreated = result.memberCreated,
                createdMember = result.createdMember
            )
        )
    }
}

data class SocialLoginResponse(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
    val memberCreated: Boolean,
    val createdMember: SocialLoginResult.CreatedMember?
) {

    data class AccessToken(
        val tokenValue: String,
        val tokenType: String,
        val tokenExpiresAt: ZonedDateTime,
    )

    data class RefreshToken(
        val tokenValue: String,
        val tokenExpiresAt: ZonedDateTime
    )
}
