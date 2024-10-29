package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.Role
import java.time.ZonedDateTime

fun memberFixtureRegistered(
    id: Long = 1L,
    email: String = "test@gmail.com",
    password: String = "1111",
    username: String = "test",
    nickname: String = "테스트유저",
    role: Role = Role.USER,
    registeredAt: ZonedDateTime = timeFixture()
): Member {
    return Member(
        id = memberIdFixture(id),
        email = emailFixture(email),
        password = encodedPasswordFixture(password),
        username = usernameFixture(username),
        nickname = nicknameFixture(nickname),
        role = role,
        registeredAt = registeredAt,
    )
}

fun memberFixtureNotRegistered(
    email: String = "test@gmail.com",
    password: String = "1111",
    username: String = "test",
    nickname: String = "테스트유저",
    role: Role = Role.USER,
    registeredAt: ZonedDateTime = timeFixture()
): Member {
    return Member(
        email = emailFixture(email),
        password = encodedPasswordFixture(password),
        username = usernameFixture(username),
        nickname = nicknameFixture(nickname),
        role = role,
        registeredAt = registeredAt,
    )
}
