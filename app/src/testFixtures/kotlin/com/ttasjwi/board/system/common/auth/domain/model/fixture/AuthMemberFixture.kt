package com.ttasjwi.board.system.common.auth.domain.model.fixture

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.auth.domain.model.Role

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
