package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.member.model.SocialConnection
import com.ttasjwi.board.system.domain.member.model.SocialServiceUser
import com.ttasjwi.board.system.domain.member.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.domain.member.service.SocialConnectionCreator
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
