package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.*
import java.time.ZonedDateTime

fun accessTokenFixture(
    memberId: Long = 1L,
    email: String = "test@gmail.com",
    username: String = "testuser",
    nickname: String = "testnick",
    role: Role = Role.USER,
    tokenValue: String = "accessToken",
    issuedAt: ZonedDateTime = timeFixture(minute = 0),
    expiresAt: ZonedDateTime = issuedAt.plusMinutes(30),
): AccessToken {
    return AccessToken.testCreate(
        memberId = memberId,
        email = email,
        username = username,
        nickname = nickname,
        role = role,
        tokenValue = tokenValue,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )
}

private fun AccessToken.Companion.testCreate(
    memberId: Long,
    email: String,
    username: String,
    nickname: String,
    role: Role,
    tokenValue: String,
    issuedAt: ZonedDateTime,
    expiresAt: ZonedDateTime
): AccessToken {
    return AccessToken(
        authMember = authMemberFixture(
            memberId = memberId,
            email = email,
            username = username,
            nickname = nickname,
            role = role,
        ),
        tokenValue = tokenValue,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )
}
