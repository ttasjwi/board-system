package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.dto.SocialLoginCommand
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.domain.member.model.SocialServiceUser
import org.springframework.stereotype.Component

@Component
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
