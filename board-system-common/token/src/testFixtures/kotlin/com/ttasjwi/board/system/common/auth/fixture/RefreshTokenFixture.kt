package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun refreshTokenFixture(
    memberId: Long = 1L,
    refreshTokenId: Long = 1L,
    tokenValue: String = "refreshTokenValue",
    issuedAt: AppDateTime = appDateTimeFixture(dayOfMonth = 1),
    expiresAt: AppDateTime = issuedAt.plusHours(24),
): RefreshToken {
    return RefreshToken.testCreate(
        memberId = memberId,
        refreshTokenId = refreshTokenId,
        tokenValue = tokenValue,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )
}

private fun RefreshToken.Companion.testCreate(
    memberId: Long,
    refreshTokenId: Long,
    tokenValue: String,
    issuedAt: AppDateTime,
    expiresAt: AppDateTime,
): RefreshToken {
    return RefreshToken(
        memberId = memberId,
        refreshTokenId = refreshTokenId,
        tokenType = VALID_TOKEN_TYPE,
        tokenValue = tokenValue,
        issuer = VALID_ISSUER,
        issuedAt = issuedAt,
        expiresAt = expiresAt
    )
}
