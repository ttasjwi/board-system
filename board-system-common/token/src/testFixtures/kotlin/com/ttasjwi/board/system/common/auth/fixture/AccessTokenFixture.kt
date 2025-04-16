package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun accessTokenFixture(
    userId: Long = 1L,
    role: Role = Role.USER,
    tokenValue: String = "accessToken",
    issuedAt: AppDateTime = appDateTimeFixture(minute = 0),
    expiresAt: AppDateTime = issuedAt.plusMinutes(30),
): AccessToken {
    return AccessToken.testCreate(
        userId = userId,
        role = role,
        tokenValue = tokenValue,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )
}

private fun AccessToken.Companion.testCreate(
    userId: Long,
    role: Role,
    tokenValue: String,
    issuedAt: AppDateTime,
    expiresAt: AppDateTime
): AccessToken {
    return AccessToken(
        authUser = authUserFixture(
            userId = userId,
            role = role,
        ),
        tokenType = VALID_TOKEN_TYPE,
        tokenValue = tokenValue,
        issuer = VALID_ISSUER,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )
}
