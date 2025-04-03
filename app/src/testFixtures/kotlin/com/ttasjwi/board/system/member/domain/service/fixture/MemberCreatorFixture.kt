package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.service.MemberCreator
import java.util.concurrent.atomic.AtomicLong

class MemberCreatorFixture : MemberCreator {

    private val sequence = AtomicLong(0)

    override fun create(
        email: String,
        password: String,
        username: String,
        nickname: String,
        currentTime: AppDateTime
    ): Member {
        return memberFixture(
            id = sequence.incrementAndGet(),
            email = email,
            password = password,
            username = username,
            nickname = nickname,
            role = Role.USER,
            registeredAt = currentTime,
        )
    }
}
