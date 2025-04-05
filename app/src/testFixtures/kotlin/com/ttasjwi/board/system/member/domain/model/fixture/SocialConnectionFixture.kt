package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialService

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
