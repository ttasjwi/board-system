package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.mapper.LoginCommandMapper
import com.ttasjwi.board.system.auth.application.processor.LoginProcessor
import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResponse
import com.ttasjwi.board.system.auth.application.usecase.LoginUseCase
import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.common.logging.getLogger
import org.springframework.stereotype.Service

@Service
internal class LoginApplicationService(
    private val commandMapper: LoginCommandMapper,
    private val processor: LoginProcessor,
) : LoginUseCase {

    companion object {
        private val log = getLogger(LoginApplicationService::class.java)
    }

    override fun login(request: LoginRequest): LoginResponse {
        log.info { "로그인 요청을 받았습니다." }

        // 유효성 검사를 거쳐서 명령으로 변환
        val command = commandMapper.mapToCommand(request)

        // 프로세서에 위임
        val event = processor.login(command)

        log.info { "로그인 됨" }

        // 처리 결과로 가공, 반환
        return makeResponse(event)
    }

    private fun makeResponse(event: LoggedInEvent): LoginResponse {
        return LoginResponse(
            accessToken = event.data.accessToken,
            accessTokenExpiresAt = event.data.accessTokenExpiresAt.toZonedDateTime(),
            refreshToken = event.data.refreshToken,
            refreshTokenExpiresAt = event.data.refreshTokenExpiresAt.toZonedDateTime(),
        )
    }
}
