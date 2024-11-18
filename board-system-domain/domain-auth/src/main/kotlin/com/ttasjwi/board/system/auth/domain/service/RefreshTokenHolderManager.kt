package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import java.time.ZonedDateTime

interface RefreshTokenHolderManager {

    fun createRefreshTokenHolder(authMember: AuthMember): RefreshTokenHolder
    fun addNewRefreshToken(refreshTokenHolder: RefreshTokenHolder, refreshToken: RefreshToken): RefreshTokenHolder
    fun changeRefreshToken(refreshTokenHolder: RefreshTokenHolder, previousToken: ZonedDateTime, newToken: RefreshToken): RefreshTokenHolder
}
