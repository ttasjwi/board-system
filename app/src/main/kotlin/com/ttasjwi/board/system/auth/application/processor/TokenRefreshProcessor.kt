package com.ttasjwi.board.system.auth.application.processor

import com.ttasjwi.board.system.auth.application.dto.TokenRefreshCommand
import com.ttasjwi.board.system.auth.domain.event.TokenRefreshedEvent
import com.ttasjwi.board.system.auth.domain.exception.RefreshTokenExpiredException
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.*
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.time.AppDateTime
import org.springframework.stereotype.Component

@Component
internal class TokenRefreshProcessor(
    private val refreshTokenManager: RefreshTokenManager,
    private val refreshTokenHolderFinder: RefreshTokenHolderFinder,
    private val accessTokenManager: AccessTokenManager,
    private val refreshTokenHolderManager: RefreshTokenHolderManager,
    private val refreshTokenHolderAppender: RefreshTokenHolderAppender,
    private val authEventCreator: AuthEventCreator,
) {
    companion object {
        private val log = getLogger(TokenRefreshProcessor::class.java)
    }

    fun tokenRefresh(command: TokenRefreshCommand): TokenRefreshedEvent {
        // 리프레시 토큰 파싱
        val refreshToken = refreshTokenManager.parse(command.refreshToken)

        // 리프레시토큰 홀더 조회
        val refreshTokenHolder = getRefreshTokenHolder(refreshToken, command.currentTime)

        // 리프레시토큰 유효성 확인
        refreshTokenManager.checkCurrentlyValid(refreshToken, refreshTokenHolder, command.currentTime)

        // 액세스 토큰 생성
        val accessToken = accessTokenManager.generate(refreshTokenHolder.authMember, command.currentTime)

        // 리프레시 토큰 재갱신 여부 확인
        val isRefreshTokenRefreshRequired = refreshTokenManager.isRefreshRequired(refreshToken, command.currentTime)

        // 엑세스 토큰 / 리프레시 토큰 재갱신 결과를 이벤트로 생성해서 반환
        // 이 때 리프레시 토큰 재갱신이 필요하면 재갱신하고, 필요하지 않으면 재갱신하지 않음
        return if (isRefreshTokenRefreshRequired) {
            handleRefreshToken(accessToken, refreshToken, refreshTokenHolder, command.currentTime)
        } else {
            authEventCreator.onTokenRefreshed(accessToken, refreshToken, false)
        }
    }

    private fun getRefreshTokenHolder(refreshToken: RefreshToken, currentTime: AppDateTime): RefreshTokenHolder {
        val findRefreshTokenHolder = refreshTokenHolderFinder.findByMemberIdOrNull(refreshToken.memberId)

        if (findRefreshTokenHolder == null) {
            val ex = RefreshTokenExpiredException(
                "리프레시 토큰 홀더가 조회되지 않았음. 따라서 리프레시토큰이 만료된 것으로 간주됨 (리프레시토큰 만료시각=${refreshToken.expiresAt},현재시각=${currentTime})"
            )
            log.warn(ex)
            throw ex
        }
        return findRefreshTokenHolder
    }

    /**
     * 리프레시 토큰을 재갱신해야할 때, 재갱신 처리
     */
    private fun handleRefreshToken(
        accessToken: AccessToken,
        refreshToken: RefreshToken,
        refreshTokenHolder: RefreshTokenHolder,
        currentTime: AppDateTime,
    ): TokenRefreshedEvent {

        // 리프레시 토큰 재생성
        val newRefreshToken = refreshTokenManager.generate(refreshToken.memberId, currentTime)

        // 리프레시 토큰 홀더에서 기존 리프레시토큰을 새로운 리프레시 토큰으로 변경
        val changedRefreshTokenHolder =
            refreshTokenHolderManager.changeRefreshToken(refreshTokenHolder, refreshToken, newRefreshToken)

        // 변경된 리프레시 홀더를 저장소에 반영
        refreshTokenHolderAppender.append(refreshToken.memberId, changedRefreshTokenHolder, currentTime)

        // 이벤트 생성 및 반환
        return authEventCreator.onTokenRefreshed(accessToken, newRefreshToken, true)
    }

}
