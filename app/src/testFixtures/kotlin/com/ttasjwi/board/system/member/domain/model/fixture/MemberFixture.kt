package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.Member
import java.time.ZonedDateTime

fun memberFixture(
    id: Long = 1L,
    email: String = "test@gmail.com",
    password: String = "1111",
    username: String = "test",
    nickname: String = "테스트유저",
    role: Role = Role.USER,
    registeredAt: ZonedDateTime = timeFixture()
): Member {
    return Member(
        id = id,
        email = emailFixture(email),
        password = encodedPasswordFixture(password),
        username = usernameFixture(username),
        nickname = nicknameFixture(nickname),
        role = role,
        registeredAt = registeredAt,
    )
}
