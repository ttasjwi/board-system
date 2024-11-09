package com.ttasjwi.board.system.member.domain.event

import com.ttasjwi.board.system.core.domain.event.DomainEvent
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent.RegisteredMemberData
import java.time.ZonedDateTime

class MemberRegisteredEvent
internal constructor(
    memberId: Long,
    email: String,
    username: String,
    nickname: String,
    roleName: String,
    registeredAt: ZonedDateTime,
) : DomainEvent<RegisteredMemberData>(
    occurredAt = registeredAt,
    data = RegisteredMemberData(
        memberId = memberId,
        email = email,
        username = username,
        nickname = nickname,
        roleName = roleName,
        registeredAt = registeredAt,
    )
) {

    class RegisteredMemberData(
        val memberId: Long,
        val email: String,
        val username: String,
        val nickname: String,
        val roleName: String,
        val registeredAt: ZonedDateTime,
    )
}
