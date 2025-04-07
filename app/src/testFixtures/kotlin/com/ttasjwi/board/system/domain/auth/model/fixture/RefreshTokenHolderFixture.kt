package com.ttasjwi.board.system.domain.auth.model.fixture

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.domain.auth.model.RefreshToken
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder

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
