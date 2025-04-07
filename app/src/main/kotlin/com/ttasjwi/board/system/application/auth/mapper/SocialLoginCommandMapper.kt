package com.ttasjwi.board.system.application.auth.mapper

import com.ttasjwi.board.system.application.auth.dto.SocialLoginCommand
import com.ttasjwi.board.system.application.auth.usecase.SocialLoginRequest
import com.ttasjwi.board.system.domain.member.model.SocialServiceUser
import com.ttasjwi.board.system.global.annotation.ApplicationCommandMapper
import com.ttasjwi.board.system.global.time.TimeManager

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
