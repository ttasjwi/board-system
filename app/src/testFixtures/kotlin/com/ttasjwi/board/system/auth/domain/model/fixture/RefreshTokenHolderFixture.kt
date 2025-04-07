package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture

fun refreshTokenHolderFixture(
    memberId: Long = 1L,
    role: Role = Role.USER,
    tokens: MutableMap<String, RefreshToken> = mutableMapOf()
): RefreshTokenHolder {
    return RefreshTokenHolder.testCreate(
        authMember = authMemberFixture(
            memberId = memberId,
            role = role
        ),
        tokens = tokens
    )
}

private fun RefreshTokenHolder.Companion.testCreate(
    authMember: AuthMember,
    tokens: MutableMap<String, RefreshToken>
): RefreshTokenHolder {
    return RefreshTokenHolder(
        authMember = authMember,
        tokens = tokens
    )
}
