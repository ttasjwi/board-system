package com.ttasjwi.board.system.member.domain.event

import com.ttasjwi.board.system.common.domain.event.DomainEvent
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent.RegisteredMemberData
import com.ttasjwi.board.system.member.domain.model.Member

class MemberRegisteredEvent
internal constructor(
    memberId: Long,
    email: String,
    username: String,
    nickname: String,
    roleName: String,
    registeredAt: AppDateTime,
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

    companion object {
        internal fun create(member: Member): MemberRegisteredEvent {
            return MemberRegisteredEvent(
                memberId = member.id,
                email = member.email,
                username = member.username.value,
                nickname = member.nickname.value,
                roleName = member.role.name,
                registeredAt = member.registeredAt,
            )
        }
    }

    class RegisteredMemberData(
        val memberId: Long,
        val email: String,
        val username: String,
        val nickname: String,
        val roleName: String,
        val registeredAt: AppDateTime,
    )
}
