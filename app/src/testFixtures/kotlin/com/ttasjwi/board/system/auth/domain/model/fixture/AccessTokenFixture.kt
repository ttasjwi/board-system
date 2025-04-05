package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

fun accessTokenFixture(
    memberId: Long = 1L,
    role: Role = Role.USER,
    tokenValue: String = "accessToken",
    issuedAt: AppDateTime = appDateTimeFixture(minute = 0),
    expiresAt: AppDateTime = issuedAt.plusMinutes(30),
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
    issuedAt: AppDateTime,
    expiresAt: AppDateTime
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
