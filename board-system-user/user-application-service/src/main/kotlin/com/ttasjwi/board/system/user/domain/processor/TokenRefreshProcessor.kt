package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.auth.*
import com.ttasjwi.board.system.user.domain.dto.TokenRefreshCommand
import com.ttasjwi.board.system.user.domain.port.MemberPersistencePort
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler

@ApplicationProcessor
internal class TokenRefreshProcessor(
    private val memberPersistencePort: MemberPersistencePort,
    private val accessTokenGeneratePort: AccessTokenGeneratePort,
    private val refreshTokenHandler: RefreshTokenHandler,
) {

    fun tokenRefresh(command: TokenRefreshCommand): Pair<AccessToken, RefreshToken> {
        val refreshToken = refreshTokenHandler.parse(command.refreshToken)
        refreshTokenHandler.throwIfExpired(refreshToken, command.currentTime)

        val authMember = getAuthMemberOrThrow(refreshToken.memberId)

        val accessToken =
            accessTokenGeneratePort.generate(authMember, command.currentTime, command.currentTime.plusMinutes(30))
        return Pair(accessToken, refreshTokenHandler.refreshIfRequired(refreshToken, command.currentTime))
    }

    private fun getAuthMemberOrThrow(memberId: Long): AuthMember {
        return memberPersistencePort.findAuthMemberOrNull(memberId)
            ?: throw RefreshTokenExpiredException("회원이 존재하지 않아서, 리프레시토큰이 더 이상 유효하지 않음")
    }
}
