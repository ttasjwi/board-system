package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.*

interface MemberCreator {

    fun create(
        email: Email,
        password: RawPassword,
        username: Username,
        nickname: Nickname,
        currentTime: AppDateTime
    ): Member
}
