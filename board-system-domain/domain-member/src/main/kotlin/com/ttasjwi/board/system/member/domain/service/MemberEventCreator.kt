package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.event.MemberRegisteredEvent
import com.ttasjwi.board.system.member.domain.model.Member

interface MemberEventCreator {
    fun onMemberRegistered(member: Member): MemberRegisteredEvent
}
