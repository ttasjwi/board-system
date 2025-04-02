package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.*
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.service.MemberCreator
import java.util.concurrent.atomic.AtomicLong

class MemberCreatorFixture : MemberCreator {

    private val sequence = AtomicLong(0)

    override fun create(
        email: String,
        password: RawPassword,
        username: Username,
        nickname: Nickname,
        currentTime: AppDateTime
    ): Member {
        return memberFixture(
            id = sequence.incrementAndGet(),
            email = email,
            password = password.value,
            username = username.value,
            nickname = nickname.value,
            role = Role.USER,
            registeredAt = currentTime,
        )
    }
}
