package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.RawPassword

interface MemberCreator {

    fun create(
        email: String,
        password: RawPassword,
        username: String,
        nickname: Nickname,
        currentTime: AppDateTime
    ): Member
}
