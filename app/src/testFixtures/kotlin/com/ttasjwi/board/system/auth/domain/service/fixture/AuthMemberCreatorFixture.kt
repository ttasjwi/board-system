package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.service.AuthMemberCreator
import com.ttasjwi.board.system.global.auth.AuthMember
import com.ttasjwi.board.system.global.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.member.domain.model.Member

class AuthMemberCreatorFixture : AuthMemberCreator {

    override fun create(member: Member): AuthMember {
        return authMemberFixture(
            memberId = member.id,
            role = member.role
        )
    }
}
