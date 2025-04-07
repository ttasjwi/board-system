package com.ttasjwi.board.system.domain.auth.service

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.domain.auth.model.RefreshToken
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder

interface RefreshTokenHolderManager {

    fun createRefreshTokenHolder(authMember: AuthMember): RefreshTokenHolder
    fun addNewRefreshToken(refreshTokenHolder: RefreshTokenHolder, refreshToken: RefreshToken): RefreshTokenHolder
    fun changeRefreshToken(
        refreshTokenHolder: RefreshTokenHolder,
        previousToken: RefreshToken,
        newToken: RefreshToken
    ): RefreshTokenHolder
}
