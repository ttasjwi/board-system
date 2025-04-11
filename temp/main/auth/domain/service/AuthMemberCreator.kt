package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.member.domain.model.Member

interface AuthMemberCreator {
    fun create(member: Member): AuthMember
}
