package com.ttasjwi.board.system.application.auth.service

import com.ttasjwi.board.system.application.auth.mapper.SocialLoginCommandMapper
import com.ttasjwi.board.system.application.auth.processor.SocialLoginProcessor
import com.ttasjwi.board.system.application.auth.usecase.SocialLoginRequest
import com.ttasjwi.board.system.application.auth.usecase.SocialLoginResponse
import com.ttasjwi.board.system.application.auth.usecase.SocialLoginUseCase
import com.ttasjwi.board.system.global.annotation.ApplicationService

@ApplicationService
internal class SocialLoginApplicationService(
    private val commandMapper: SocialLoginCommandMapper,
    private val processor: SocialLoginProcessor,
) : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResponse {
        val command = commandMapper.mapToCommand(request)

        return processor.socialLogin(command)
    }
}
