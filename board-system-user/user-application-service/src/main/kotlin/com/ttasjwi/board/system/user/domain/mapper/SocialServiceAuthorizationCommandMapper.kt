package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.user.domain.dto.SocialServiceAuthorizationCommand

@ApplicationCommandMapper
class SocialServiceAuthorizationCommandMapper(
    private val timeManager: TimeManager,
) {

    fun mapToCommand(socialServiceId: String): SocialServiceAuthorizationCommand {
        return SocialServiceAuthorizationCommand(
            oAuth2ClientRegistrationId = socialServiceId,
            currentTime = timeManager.now(),
        )
    }
}
