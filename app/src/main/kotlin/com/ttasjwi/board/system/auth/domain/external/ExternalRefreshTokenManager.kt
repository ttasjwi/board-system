package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import java.time.ZonedDateTime

interface ExternalRefreshTokenManager {

    fun generate(memberId: Long, refreshTokenId: RefreshTokenId, issuedAt: ZonedDateTime, expiresAt: ZonedDateTime): RefreshToken
    fun parse(tokenValue: String): RefreshToken
}
