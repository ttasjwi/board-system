package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.member.model.SocialConnection
import com.ttasjwi.board.system.domain.member.model.SocialServiceUser

interface SocialConnectionCreator {

    fun create(
        memberId: Long,
        socialServiceUser: SocialServiceUser,
        currentTime: AppDateTime
    ): SocialConnection
}
