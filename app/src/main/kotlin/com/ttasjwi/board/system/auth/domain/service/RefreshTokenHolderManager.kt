package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.common.auth.AuthMember

interface RefreshTokenHolderManager {

    fun createRefreshTokenHolder(authMember: AuthMember): RefreshTokenHolder
    fun addNewRefreshToken(refreshTokenHolder: RefreshTokenHolder, refreshToken: RefreshToken): RefreshTokenHolder
    fun changeRefreshToken(
        refreshTokenHolder: RefreshTokenHolder,
        previousToken: RefreshToken,
        newToken: RefreshToken
    ): RefreshTokenHolder
}
