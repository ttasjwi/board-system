package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.service.AuthMemberCreator
import com.ttasjwi.board.system.domain.member.model.Member
import com.ttasjwi.board.system.global.annotation.DomainService
import com.ttasjwi.board.system.global.auth.AuthMember

@DomainService
internal class AuthMemberCreatorImpl : AuthMemberCreator {

    override fun create(member: Member): AuthMember {
        return CreatedAuthMember(member)
    }

    private class CreatedAuthMember(
        member: Member
    ) : AuthMember(
        memberId = member.id,
        role = member.role,
    )
}
