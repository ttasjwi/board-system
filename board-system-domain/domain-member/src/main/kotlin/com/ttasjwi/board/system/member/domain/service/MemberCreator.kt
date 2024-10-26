package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.email.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.model.Username
import java.time.ZonedDateTime

interface MemberCreator {

    fun create(
        email: Email,
        emailVerified: Boolean,
        password: RawPassword,
        username: Username,
        nickname: Nickname,
        currentTime: ZonedDateTime
    ): Member
}
