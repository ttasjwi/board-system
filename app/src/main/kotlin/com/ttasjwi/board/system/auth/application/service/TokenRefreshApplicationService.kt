package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.mapper.TokenRefreshCommandMapper
import com.ttasjwi.board.system.auth.application.processor.TokenRefreshProcessor
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshResponse
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshUseCase
import com.ttasjwi.board.system.auth.domain.event.TokenRefreshedEvent
import com.ttasjwi.board.system.common.application.TransactionRunner
import com.ttasjwi.board.system.common.logging.getLogger
import org.springframework.stereotype.Service

@Service
internal class TokenRefreshApplicationService(
    private val commandMapper: TokenRefreshCommandMapper,
    private val processor: TokenRefreshProcessor,
    private val transactionRunner: TransactionRunner,
) : TokenRefreshUseCase {

    companion object {
        private val log = getLogger(TokenRefreshApplicationService::class.java)
    }

    override fun tokenRefresh(request: TokenRefreshRequest): TokenRefreshResponse {
        log.info { "토큰 재갱신 요청을 받았습니다." }

        // 유효성 검사를 거쳐서 명령으로 변환
        val command = commandMapper.mapToCommand(request)

        // 프로세서에 위임
        val event = transactionRunner.run {
            processor.tokenRefresh(command)
        }

        log.info { "토큰 재갱신됨" }

        // 응답 생성, 반환
        return makeResponse(event)
    }

    private fun makeResponse(event: TokenRefreshedEvent): TokenRefreshResponse {
        return TokenRefreshResponse(
            accessToken = event.data.accessToken,
            accessTokenExpiresAt = event.data.accessTokenExpiresAt.toZonedDateTime(),
            refreshToken = event.data.refreshToken,
            refreshTokenExpiresAt = event.data.refreshTokenExpiresAt.toZonedDateTime(),
            refreshTokenRefreshed = event.data.refreshTokenRefreshed,
        )
    }
}
