package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.user.domain.mapper.SocialServiceAuthorizationCommandMapper
import com.ttasjwi.board.system.user.domain.processor.SocialServiceAuthorizationProcessor

@UseCase
class SocialServiceAuthorizationUseCaseImpl(
    private val commandMapper: SocialServiceAuthorizationCommandMapper,
    private val processor: SocialServiceAuthorizationProcessor,
) : SocialServiceAuthorizationUseCase {

    override fun generateAuthorizationRequestUri(socialServiceId: String): String {
        val command = commandMapper.mapToCommand(socialServiceId)
        val oAuth2AuthorizationRequest = processor.generateOAuth2AuthorizationRequest(command)
        return oAuth2AuthorizationRequest.authorizationRequestUri
    }
}
