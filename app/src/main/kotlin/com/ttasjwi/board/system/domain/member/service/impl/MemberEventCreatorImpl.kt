package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.domain.member.event.MemberRegisteredEvent
import com.ttasjwi.board.system.domain.member.model.Member
import com.ttasjwi.board.system.domain.member.service.MemberEventCreator
import com.ttasjwi.board.system.global.annotation.DomainService

@DomainService
internal class MemberEventCreatorImpl : MemberEventCreator {

    override fun onMemberRegistered(member: Member): MemberRegisteredEvent {
        return MemberRegisteredEvent.create(member)
    }
}
