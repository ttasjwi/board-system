package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.dto.SocialLoginCommand
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.core.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.core.time.TimeManager
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.service.EmailCreator

@ApplicationCommandMapper
internal class SocialLoginCommandMapper(
    private val emailCreator: EmailCreator,
    private val timeManager: TimeManager,
) {

    fun mapToCommand(request: SocialLoginRequest): SocialLoginCommand {
        val socialServiceUser = SocialServiceUser.restore(request.socialServiceName, request.socialServiceUserId)
        val email = emailCreator.create(request.email).getOrThrow()

        return SocialLoginCommand(
            socialServiceUser = socialServiceUser,
            email = email,
            currentTime = timeManager.now()
        )
    }
}
