package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.member.model.Member

interface MemberCreator {

    fun create(
        email: String,
        password: String,
        username: String,
        nickname: String,
        currentTime: AppDateTime
    ): Member
}
