package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent
import com.ttasjwi.board.system.member.domain.event.fixture.memberRegisteredEventFixture
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.service.MemberEventCreator

class MemberEventCreatorFixture : MemberEventCreator {

    override fun onMemberRegistered(member: Member): MemberRegisteredEvent {
        return memberRegisteredEventFixture(
            memberId = member.id!!.value,
            email = member.email.value,
            username = member.username.value,
            nickname = member.nickname.value,
            role = member.role,
            registeredAt = member.registeredAt,
        )
    }
}
