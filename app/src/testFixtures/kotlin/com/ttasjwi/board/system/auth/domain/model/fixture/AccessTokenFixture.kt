package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import java.time.ZonedDateTime

fun accessTokenFixture(
    memberId: Long = 1L,
    role: Role = Role.USER,
    tokenValue: String = "accessToken",
    issuedAt: ZonedDateTime = timeFixture(minute = 0),
    expiresAt: ZonedDateTime = issuedAt.plusMinutes(30),
): AccessToken {
    return AccessToken.testCreate(
        memberId = memberId,
        role = role,
        tokenValue = tokenValue,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )
}

private fun AccessToken.Companion.testCreate(
    memberId: Long,
    role: Role,
    tokenValue: String,
    issuedAt: ZonedDateTime,
    expiresAt: ZonedDateTime
): AccessToken {
    return AccessToken(
        authMember = authMemberFixture(
            memberId = memberId,
            role = role,
        ),
        tokenValue = tokenValue,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )
}
