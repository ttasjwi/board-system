package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.MemberId
import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.service.SocialConnectionCreator
import java.time.ZonedDateTime

@DomainService
class SocialConnectionCreatorImpl : SocialConnectionCreator {

    override fun create(
        memberId: MemberId,
        socialServiceUser: SocialServiceUser,
        currentTime: ZonedDateTime
    ): SocialConnection {
        return SocialConnection.create(
            memberId = memberId,
            socialServiceUser = socialServiceUser,
            currentTime = currentTime
        )
    }
}
