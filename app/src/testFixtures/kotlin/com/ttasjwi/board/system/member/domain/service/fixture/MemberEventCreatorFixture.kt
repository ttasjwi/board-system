package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent
import com.ttasjwi.board.system.member.domain.event.fixture.memberRegisteredEventFixture
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.service.MemberEventCreator

class MemberEventCreatorFixture : MemberEventCreator {

    override fun onMemberRegistered(member: Member): MemberRegisteredEvent {
        return memberRegisteredEventFixture(
            memberId = member.id,
            email = member.email,
            username = member.username,
            nickname = member.nickname.value,
            role = member.role,
            registeredAt = member.registeredAt,
        )
    }
}
