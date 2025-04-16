package com.ttasjwi.board.system.user.domain.model.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.model.SocialService

fun socialConnectionFixture(
    id: Long = 1L,
    memberId: Long = 1L,
    socialService: SocialService = SocialService.GOOGLE,
    socialServiceUserId: String = "asdfadfad",
    linkedAt: AppDateTime = appDateTimeFixture(minute = 0)
): SocialConnection {
    return SocialConnection(
        socialConnectionId = id,
        memberId = memberId,
        socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
        linkedAt = linkedAt
    )
}
