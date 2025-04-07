package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.domain.member.model.Member
import com.ttasjwi.board.system.global.auth.AuthMember

interface AuthMemberCreator {
    fun create(member: Member): AuthMember
}
