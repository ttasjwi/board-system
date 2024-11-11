package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.service.AuthMemberCreator
import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.Member

@DomainService
internal class AuthMemberCreatorImpl : AuthMemberCreator {

    override fun create(member: Member): AuthMember {
        TODO("Not yet implemented")
    }
}
