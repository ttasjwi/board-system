package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.event.MemberRegisteredEvent
import com.ttasjwi.board.system.domain.member.model.Member

interface MemberEventCreator {
    fun onMemberRegistered(member: Member): MemberRegisteredEvent
}
