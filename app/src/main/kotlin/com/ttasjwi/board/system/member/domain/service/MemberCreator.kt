package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Member

interface MemberCreator {

    fun create(
        email: String,
        password: String,
        username: String,
        nickname: String,
        currentTime: AppDateTime
    ): Member
}
