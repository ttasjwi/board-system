package com.ttasjwi.board.system.spring.security.authentication

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResponse
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginUseCase
import com.ttasjwi.board.system.spring.security.oauth2.SocialServiceUserPrincipal
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class CustomOAuth2LoginAuthenticationSuccessHandler(
    private val useCase: SocialLoginUseCase,
) : AuthenticationSuccessHandler {

    companion object {
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

        val socialLoginResponse = useCase.socialLogin(socialLoginRequest)

        // Http 응답 작성
        writeResponse(socialLoginResponse, response)
    }

    private fun writeResponse(
        socialLoginResponse: SocialLoginResponse,
        response: HttpServletResponse
    ) {
        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(socialLoginResponse))
    }
}
