package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.domain.member.event.MemberRegisteredEvent
import com.ttasjwi.board.system.domain.member.event.fixture.memberRegisteredEventFixture
import com.ttasjwi.board.system.domain.member.model.Member
import com.ttasjwi.board.system.domain.member.service.MemberEventCreator

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
