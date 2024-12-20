package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import java.time.ZonedDateTime

fun refreshTokenFixture(
    memberId: Long = 1L,
    refreshTokenId: String = "refreshToken1234",
    tokenValue: String = "refreshTokenValue",
    issuedAt: ZonedDateTime = timeFixture(dayOfMonth = 1),
    expiresAt: ZonedDateTime = issuedAt.plusHours(24),
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
    issuedAt: ZonedDateTime,
    expiresAt: ZonedDateTime,
): RefreshToken {
    return RefreshToken(
        memberId = memberIdFixture(memberId),
        refreshTokenId = refreshTokenIdFixture(refreshTokenId),
        tokenValue = tokenValue,
        issuedAt = issuedAt,
        expiresAt = expiresAt
    )
}
