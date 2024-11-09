package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.*
import com.ttasjwi.board.system.member.domain.service.MemberCreator
import com.ttasjwi.board.system.member.domain.service.PasswordManager
import java.time.ZonedDateTime

@DomainService
class MemberCreatorImpl(
    private val passwordManager: PasswordManager,
) : MemberCreator {

    override fun create(
        email: Email,
        password: RawPassword,
        username: Username,
        nickname: Nickname,
        currentTime: ZonedDateTime
    ): Member {
        return Member.create(
            email = email,
            password = passwordManager.encode(password),
            username = username,
            nickname = nickname,
            registeredAt = currentTime,
        )
    }
}
