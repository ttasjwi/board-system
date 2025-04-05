package com.ttasjwi.board.system.global.auth.fixture

import com.ttasjwi.board.system.global.auth.AuthMember
import com.ttasjwi.board.system.global.auth.Role

fun authMemberFixture(
    memberId: Long = 1L,
    role: Role = Role.USER,
): AuthMember {
    return TestAuthMember(
        memberId = memberId,
        role = role,
    )
}

private class TestAuthMember(
    memberId: Long,
    role: Role
) : AuthMember(
    memberId = memberId,
    role = role
)
