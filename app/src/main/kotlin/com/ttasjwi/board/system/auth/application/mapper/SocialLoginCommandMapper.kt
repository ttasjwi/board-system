package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.dto.SocialLoginCommand
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.global.annotation.ApplicationCommandMapper
import com.ttasjwi.board.system.global.time.TimeManager
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser

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
