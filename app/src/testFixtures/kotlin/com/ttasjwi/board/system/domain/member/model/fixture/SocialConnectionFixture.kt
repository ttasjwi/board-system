package com.ttasjwi.board.system.domain.member.model.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.member.model.SocialConnection
import com.ttasjwi.board.system.domain.member.model.SocialService

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
