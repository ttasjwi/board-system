package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.Member
import com.ttasjwi.board.system.global.time.AppDateTime

interface MemberCreator {

    fun create(
        email: String,
        password: String,
        username: String,
        nickname: String,
        currentTime: AppDateTime
    ): Member
}
