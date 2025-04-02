package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.mapper.SocialLoginCommandMapper
import com.ttasjwi.board.system.auth.application.processor.SocialLoginProcessor
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResponse
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginUseCase
import com.ttasjwi.board.system.common.application.TransactionRunner
import org.springframework.stereotype.Service

@Service
internal class SocialLoginApplicationService(
    private val commandMapper: SocialLoginCommandMapper,
    private val processor: SocialLoginProcessor,
    private val transactionRunner: TransactionRunner,
) : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResponse {

        val command = commandMapper.mapToCommand(request)

        return transactionRunner.run {
            processor.socialLogin(command)
        }
    }
}
