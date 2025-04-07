package com.ttasjwi.board.system.domain.member.event.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.member.event.MemberRegisteredEvent

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
