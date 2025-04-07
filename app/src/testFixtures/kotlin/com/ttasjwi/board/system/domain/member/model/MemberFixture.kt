package com.ttasjwi.board.system.domain.member.model

import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

fun memberFixture(
    id: Long = 1L,
    email: String = "test@gmail.com",
    password: String = "1111",
    username: String = "test",
    nickname: String = "테스트유저",
    role: Role = Role.USER,
    registeredAt: AppDateTime = appDateTimeFixture()
): Member {
    return Member(
        id = id,
        email = email,
        password = password,
        username = username,
        nickname = nickname,
        role = role,
        registeredAt = registeredAt,
    )
}
