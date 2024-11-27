package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.MemberId
import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.model.fixture.socialConnectionFixtureNotSaved
import com.ttasjwi.board.system.member.domain.service.SocialConnectionCreator
import java.time.ZonedDateTime

class SocialConnectionCreatorFixture : SocialConnectionCreator {

    override fun create(
        memberId: MemberId,
        socialServiceUser: SocialServiceUser,
        currentTime: ZonedDateTime
    ): SocialConnection {
        return socialConnectionFixtureNotSaved(
            memberId = memberId.value,
            socialService = socialServiceUser.service,
            socialServiceUserId = socialServiceUser.userId,
            linkedAt = currentTime,
        )
    }
}
