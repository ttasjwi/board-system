package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.user.domain.mapper.TokenRefreshCommandMapper
import com.ttasjwi.board.system.user.domain.processor.TokenRefreshProcessor

@UseCase
internal class TokenRefreshUseCaseImpl(
    private val commandMapper: TokenRefreshCommandMapper,
    private val processor: TokenRefreshProcessor,
) : TokenRefreshUseCase {

    override fun tokenRefresh(request: TokenRefreshRequest): TokenRefreshResponse {
        val command = commandMapper.mapToCommand(request)
        val (accessToken, refreshToken) = processor.tokenRefresh(command)
        return makeResponse(accessToken, refreshToken, command.refreshToken)
    }

    private fun makeResponse(
        accessToken: AccessToken,
        refreshToken: RefreshToken,
        prevRefreshTokenValue: String
    ): TokenRefreshResponse {
        return TokenRefreshResponse(
            accessToken = accessToken.tokenValue,
            accessTokenType = "Bearer",
            accessTokenExpiresAt = accessToken.expiresAt.toZonedDateTime(),
            refreshToken = refreshToken.tokenValue,
            refreshTokenExpiresAt = refreshToken.expiresAt.toZonedDateTime(),
            refreshTokenRefreshed = refreshToken.tokenValue != prevRefreshTokenValue,
        )
    }
}
