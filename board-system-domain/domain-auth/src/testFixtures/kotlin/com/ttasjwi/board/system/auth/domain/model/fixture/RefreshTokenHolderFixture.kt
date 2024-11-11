package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import com.ttasjwi.board.system.member.domain.model.Role

fun refreshTokenHolderFixture(
    memberId: Long = 1L,
    email: String = "hello@gmail.com",
    username: String = "username",
    nickname: String = "nickname",
    role: Role = Role.USER,
    tokens: MutableMap<RefreshTokenId, RefreshToken> = mutableMapOf()
): RefreshTokenHolder {
    return RefreshTokenHolder.testCreate(
        authMember = authMemberFixture(
            memberId = memberId,
            email = email,
            username = username,
            nickname = nickname,
            role = role
        ),
        tokens = tokens
    )
}

private fun RefreshTokenHolder.Companion.testCreate(
    authMember: AuthMember,
    tokens: MutableMap<RefreshTokenId, RefreshToken>
): RefreshTokenHolder {
    return RefreshTokenHolder(
        authMember = authMember,
        tokens = tokens
    )
}
