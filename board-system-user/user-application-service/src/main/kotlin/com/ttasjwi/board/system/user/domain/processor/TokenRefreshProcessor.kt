package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.auth.*
import com.ttasjwi.board.system.user.domain.dto.TokenRefreshCommand
import com.ttasjwi.board.system.user.domain.port.UserPersistencePort
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler

@ApplicationProcessor
internal class TokenRefreshProcessor(
    private val userPersistencePort: UserPersistencePort,
    private val accessTokenGeneratePort: AccessTokenGeneratePort,
    private val refreshTokenHandler: RefreshTokenHandler,
) {

    fun tokenRefresh(command: TokenRefreshCommand): Pair<AccessToken, RefreshToken> {
        val refreshToken = refreshTokenHandler.parse(command.refreshToken)
        refreshTokenHandler.throwIfExpired(refreshToken, command.currentTime)

        val authUser = getAuthUserOrThrow(refreshToken.userId)

        val accessToken =
            accessTokenGeneratePort.generate(authUser, command.currentTime, command.currentTime.plusMinutes(30))
        return Pair(accessToken, refreshTokenHandler.refreshIfRequired(refreshToken, command.currentTime))
    }

    private fun getAuthUserOrThrow(userId: Long): AuthUser {
        return userPersistencePort.findAuthUserOrNull(userId)
            ?: throw RefreshTokenExpiredException("회원이 존재하지 않아서, 리프레시토큰이 더 이상 유효하지 않음")
    }
}
