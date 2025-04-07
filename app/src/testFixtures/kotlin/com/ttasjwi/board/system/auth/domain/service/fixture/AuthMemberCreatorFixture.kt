package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.service.AuthMemberCreator
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.domain.member.model.Member

class AuthMemberCreatorFixture : AuthMemberCreator {

    override fun create(member: Member): AuthMember {
        return authMemberFixture(
            memberId = member.id,
            role = member.role
        )
    }
}
