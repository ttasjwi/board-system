package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.user.domain.dto.SocialServiceAuthorizationCommand
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.port.OAuth2AuthorizationRequestPersistencePort
import com.ttasjwi.board.system.user.domain.port.OAuth2ClientRegistrationPersistencePort

@ApplicationProcessor
class SocialServiceAuthorizationProcessor(
    private val oAuth2ClientRegistrationPersistencePort: OAuth2ClientRegistrationPersistencePort,
    private val oAuth2AuthorizationRequestPersistencePort: OAuth2AuthorizationRequestPersistencePort,
) {

    fun generateOAuth2AuthorizationRequest(command: SocialServiceAuthorizationCommand): OAuth2AuthorizationRequest {
        // Client Registration 찾기, 조회 실패 시 예외 발생
        val clientRegistration = getClientRegistrationOrThrow(command.oAuth2ClientRegistrationId)

        // OAuth2 인가요청 생성
        val oauth2AuthorizationRequest = OAuth2AuthorizationRequest.create(clientRegistration)

        // OAuth2 인가요청 저장 (5분간 유효)
        oAuth2AuthorizationRequestPersistencePort.save(oauth2AuthorizationRequest, command.currentTime.plusMinutes(5))

        return oauth2AuthorizationRequest
    }

    private fun getClientRegistrationOrThrow(socialServiceId: String): OAuth2ClientRegistration {
        return oAuth2ClientRegistrationPersistencePort.findById(socialServiceId)
            ?: throw NoSuchElementException("SocialServiceId $socialServiceId not found")
    }
}
