package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.service.MemberEventCreator

@DomainService
internal class MemberEventCreatorImpl : MemberEventCreator {

    override fun onMemberRegistered(member: Member): MemberRegisteredEvent {
        return MemberRegisteredEvent.create(member)
    }
}
