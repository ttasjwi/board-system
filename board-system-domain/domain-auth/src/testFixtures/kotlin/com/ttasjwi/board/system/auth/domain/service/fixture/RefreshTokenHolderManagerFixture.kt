package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderManager
import java.time.ZonedDateTime

class RefreshTokenHolderManagerFixture : RefreshTokenHolderManager {

    override fun createRefreshTokenHolder(authMember: AuthMember): RefreshTokenHolder {
        return refreshTokenHolderFixture(
            memberId = authMember.memberId.value,
            role = authMember.role,
            tokens = mutableMapOf()
        )
    }

    override fun addNewRefreshToken(refreshTokenHolder: RefreshTokenHolder, refreshToken: RefreshToken): RefreshTokenHolder {
        val tokens = refreshTokenHolder.getTokens().toMutableMap()
        tokens[refreshToken.refreshTokenId] = refreshToken

        return refreshTokenHolderFixture(
            memberId = refreshTokenHolder.authMember.memberId.value,
            role = refreshTokenHolder.authMember.role,
            tokens = tokens
        )
    }

    override fun changeRefreshToken(
        refreshTokenHolder: RefreshTokenHolder,
        previousToken: ZonedDateTime,
        newToken: RefreshToken
    ): RefreshTokenHolder {
        TODO("Not yet implemented")
    }
}
