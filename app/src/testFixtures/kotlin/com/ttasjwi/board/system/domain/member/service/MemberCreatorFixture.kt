package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.Member
import com.ttasjwi.board.system.domain.member.model.memberFixture
import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.time.AppDateTime
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
