package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.common.dataserializer.DataSerializer
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.SocialLoginResponse
import com.ttasjwi.board.system.user.domain.SocialLoginUseCase
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.principal.SocialServiceUserPrincipal
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class SocialLoginSuccessHandler(
    private val useCase: SocialLoginUseCase,
) : AuthenticationSuccessHandler {

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
        response.writer.write(DataSerializer.serialize(socialLoginResponse))
    }
}
