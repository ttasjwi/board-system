package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.member.domain.model.MemberId
import com.ttasjwi.board.system.member.domain.model.Role
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture

fun authMemberFixture(
    memberId: Long = 1L,
    role: Role = Role.USER,
): AuthMember {
    return TestAuthMember(
        memberId = memberIdFixture(memberId),
        role = role,
    )
}

private class TestAuthMember(
    memberId: MemberId,
    role: Role
) : AuthMember(
    memberId = memberId,
    role = role
)
