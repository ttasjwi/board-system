package com.ttasjwi.board.system.member.domain.event.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent
import com.ttasjwi.board.system.member.domain.model.Role
import java.time.ZonedDateTime

fun memberRegisteredEventFixture(
    memberId: Long = 1L,
    email: String = "hello@gmail.com",
    username: String = "username",
    nickname: String = "테스트닉네임",
    role: Role = Role.USER,
    registeredAt: ZonedDateTime = timeFixture(),
): MemberRegisteredEvent {
    return MemberRegisteredEvent(
        memberId = memberId,
        email = email,
        username = username,
        nickname = nickname,
        roleName = role.name,
        registeredAt = registeredAt,
    )
}
