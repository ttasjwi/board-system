package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.email.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.model.Username
import com.ttasjwi.board.system.member.domain.service.MemberCreator
import java.time.ZonedDateTime

@DomainService
class MemberCreatorImpl : MemberCreator {

    override fun create(
        email: Email,
        emailVerified: Boolean,
        password: RawPassword,
        username: Username,
        nickname: Nickname,
        currentTime: ZonedDateTime
    ): Member {
        TODO("Not yet implemented")
    }
}
