package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.event.MemberRegisteredEvent
import com.ttasjwi.board.system.domain.member.event.memberRegisteredEventFixture
import com.ttasjwi.board.system.domain.member.model.Member

class MemberEventCreatorFixture : MemberEventCreator {

    override fun onMemberRegistered(member: Member): MemberRegisteredEvent {
        return memberRegisteredEventFixture(
            memberId = member.id,
            email = member.email,
            username = member.username,
            nickname = member.nickname,
            role = member.role,
            registeredAt = member.registeredAt,
        )
    }
}
