package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.MemberId
import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import java.time.ZonedDateTime

interface SocialConnectionCreator {

    fun create(
        memberId: MemberId,
        socialServiceUser: SocialServiceUser,
        currentTime: ZonedDateTime
    ): SocialConnection
}
