package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.dto.SocialLoginCommand
import com.ttasjwi.board.system.user.domain.model.SocialServiceUser

@ApplicationCommandMapper
internal class SocialLoginCommandMapper(
    private val timeManager: TimeManager,
) {

    fun mapToCommand(request: SocialLoginRequest): SocialLoginCommand {
        val socialServiceUser = SocialServiceUser.restore(request.socialServiceName, request.socialServiceUserId)

        return SocialLoginCommand(
            socialServiceUser = socialServiceUser,
            email = request.email,
            currentTime = timeManager.now()
        )
    }
}
