package com.ttasjwi.board.system.member.domain.event.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent
import java.time.ZonedDateTime

fun memberRegisteredEventFixture(
    registeredAt: ZonedDateTime = timeFixture(),
    memberId: Long = 1L,
    email: String = "hello@gmail.com",
    username: String = "username",
    nickname: String = "테스트닉네임",
): MemberRegisteredEvent {
    return MemberRegisteredEvent(
        registeredAt = registeredAt,
        memberId = memberId,
        email = email,
        username = username,
        nickname = nickname,
    )
}
