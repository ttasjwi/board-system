package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role

fun authUserFixture(
    userId: Long = 1L,
    role: Role = Role.USER,
): AuthUser {
    return AuthUser(
        userId = userId,
        role = role,
    )
}
