package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.service.AuthMemberCreator
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.member.domain.model.Member

@DomainService
internal class AuthMemberCreatorImpl : AuthMemberCreator {

    override fun create(member: Member): AuthMember {
        return AuthMember.create(member.id, member.role)
    }
}
