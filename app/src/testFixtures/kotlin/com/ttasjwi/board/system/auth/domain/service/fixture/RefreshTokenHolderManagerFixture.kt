package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderManager
import com.ttasjwi.board.system.global.auth.AuthMember

class RefreshTokenHolderManagerFixture : RefreshTokenHolderManager {

    override fun createRefreshTokenHolder(authMember: AuthMember): RefreshTokenHolder {
        return refreshTokenHolderFixture(
            memberId = authMember.memberId,
            role = authMember.role,
            tokens = mutableMapOf()
        )
    }

    override fun addNewRefreshToken(
        refreshTokenHolder: RefreshTokenHolder,
        refreshToken: RefreshToken
    ): RefreshTokenHolder {
        val tokens = refreshTokenHolder.getTokens().toMutableMap()
        tokens[refreshToken.refreshTokenId] = refreshToken

        return refreshTokenHolderFixture(
            memberId = refreshTokenHolder.authMember.memberId,
            role = refreshTokenHolder.authMember.role,
            tokens = tokens
        )
    }

    override fun changeRefreshToken(
        refreshTokenHolder: RefreshTokenHolder,
        previousToken: RefreshToken,
        newToken: RefreshToken
    ): RefreshTokenHolder {

        val tokens = refreshTokenHolder.getTokens().toMutableMap()
        tokens.remove(previousToken.refreshTokenId)
        tokens[newToken.refreshTokenId] = newToken

        return refreshTokenHolderFixture(
            memberId = refreshTokenHolder.authMember.memberId,
            role = refreshTokenHolder.authMember.role,
            tokens = tokens
        )
    }
}
