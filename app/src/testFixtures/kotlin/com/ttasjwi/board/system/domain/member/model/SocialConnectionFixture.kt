package com.ttasjwi.board.system.domain.member.model

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

fun socialConnectionFixture(
    id: Long = 1L,
    memberId: Long = 1L,
    socialService: SocialService = SocialService.GOOGLE,
    socialServiceUserId: String = "asdfadfad",
    linkedAt: AppDateTime = appDateTimeFixture(minute = 0)
): SocialConnection {
    return SocialConnection(
        id = id,
        memberId = memberId,
        socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
        linkedAt = linkedAt
    )
}
