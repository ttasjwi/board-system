package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.Role

fun authMemberFixture(
    memberId: Long = 1L,
    role: Role = Role.USER,
): AuthMember {
    return AuthMember(
        memberId = memberId,
        role = role,
    )
}
