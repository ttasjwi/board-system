package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.mapper.LoginCommandMapper
import com.ttasjwi.board.system.auth.application.processor.LoginProcessor
import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResult
import com.ttasjwi.board.system.auth.application.usecase.LoginUseCase
import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.core.application.TransactionRunner
import com.ttasjwi.board.system.logging.getLogger

@ApplicationService
internal class LoginApplicationService(
    private val commandMapper: LoginCommandMapper,
    private val processor: LoginProcessor,
    private val transactionRunner: TransactionRunner,
) : LoginUseCase {

    companion object {
        private val log = getLogger(LoginApplicationService::class.java)
    }

    override fun login(request: LoginRequest): LoginResult {
        log.info { "로그인 요청을 받았습니다." }

        // 유효성 검사를 거쳐서 명령으로 변환
        val command = commandMapper.mapToCommand(request)

        // 프로세서에 위임
        val event = transactionRunner.run {
            processor.login(command)
        }

        log.info { "로그인 됨" }

        // 처리 결과로 가공, 반환
        return makeResult(event)
    }

    private fun makeResult(event: LoggedInEvent): LoginResult {
        return LoginResult(
            accessToken = event.data.accessToken,
            accessTokenExpiresAt = event.data.accessTokenExpiresAt,
            refreshToken = event.data.refreshToken,
            refreshTokenExpiresAt = event.data.refreshTokenExpiresAt,
        )
    }
}
