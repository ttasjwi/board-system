package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.member.domain.model.*
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixtureNotRegistered
import com.ttasjwi.board.system.member.domain.service.MemberCreator
import java.time.ZonedDateTime

class MemberCreatorFixture : MemberCreator {

    override fun create(
        email: Email,
        password: RawPassword,
        username: Username,
        nickname: Nickname,
        currentTime: ZonedDateTime
    ): Member {
        return memberFixtureNotRegistered(
            email = email.value,
            password = password.value,
            username = username.value,
            nickname = nickname.value,
            role = Role.USER,
            registeredAt = currentTime,
        )
    }
}
