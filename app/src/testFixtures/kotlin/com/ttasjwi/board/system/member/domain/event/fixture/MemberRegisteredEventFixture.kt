package com.ttasjwi.board.system.member.domain.event.fixture

import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent

fun memberRegisteredEventFixture(
    memberId: Long = 1L,
    email: String = "hello@gmail.com",
    username: String = "username",
    nickname: String = "테스트닉네임",
    role: Role = Role.USER,
    registeredAt: AppDateTime = appDateTimeFixture(),
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
