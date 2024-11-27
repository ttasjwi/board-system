package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.mapper.SocialLoginCommandMapper
import com.ttasjwi.board.system.auth.application.processor.SocialLoginProcessor
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResult
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginUseCase
import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.core.application.TransactionRunner

@ApplicationService
internal class SocialLoginApplicationService(
    private val commandMapper: SocialLoginCommandMapper,
    private val processor: SocialLoginProcessor,
    private val transactionRunner: TransactionRunner,
) : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResult {

        val command = commandMapper.mapToCommand(request)

        return transactionRunner.run {
            processor.socialLogin(command)
        }
    }
}
