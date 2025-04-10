package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.member.domain.service.SocialConnectionCreator
import java.util.concurrent.atomic.AtomicLong

class SocialConnectionCreatorFixture : SocialConnectionCreator {

    private val sequence: AtomicLong = AtomicLong(0)

    override fun create(
        memberId: Long,
        socialServiceUser: SocialServiceUser,
        currentTime: AppDateTime
    ): SocialConnection {
        return socialConnectionFixture(
            id = sequence.incrementAndGet(),
            memberId = memberId,
            socialService = socialServiceUser.service,
            socialServiceUserId = socialServiceUser.userId,
            linkedAt = currentTime,
        )
    }
}
