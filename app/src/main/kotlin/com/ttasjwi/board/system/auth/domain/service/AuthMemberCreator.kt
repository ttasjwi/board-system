package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.domain.member.model.Member

interface AuthMemberCreator {
    fun create(member: Member): AuthMember
}
