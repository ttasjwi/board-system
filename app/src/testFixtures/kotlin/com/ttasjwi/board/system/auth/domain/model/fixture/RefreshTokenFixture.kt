package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun refreshTokenFixture(
    memberId: Long = 1L,
    refreshTokenId: String = "refreshToken1234",
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
    refreshTokenId: String,
    tokenValue: String,
    issuedAt: AppDateTime,
    expiresAt: AppDateTime,
): RefreshToken {
    return RefreshToken(
        memberId = memberId,
        refreshTokenId = refreshTokenIdFixture(refreshTokenId),
        tokenValue = tokenValue,
        issuedAt = issuedAt,
        expiresAt = expiresAt
    )
}
