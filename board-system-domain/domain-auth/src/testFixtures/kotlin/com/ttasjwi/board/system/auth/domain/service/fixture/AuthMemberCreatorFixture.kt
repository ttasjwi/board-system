package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.auth.domain.service.AuthMemberCreator
import com.ttasjwi.board.system.member.domain.model.Member

class AuthMemberCreatorFixture : AuthMemberCreator {

    override fun create(member: Member): AuthMember {
        return authMemberFixture(
            memberId = member.id!!.value,
            email = member.email.value,
            username = member.username.value,
            nickname = member.nickname.value,
            role = member.role
        )
    }
}
