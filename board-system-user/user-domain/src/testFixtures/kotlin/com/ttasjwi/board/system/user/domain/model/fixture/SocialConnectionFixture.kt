package com.ttasjwi.board.system.user.domain.model.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.model.SocialService

fun socialConnectionFixture(
    socialConnectionId: Long = 1L,
    userId: Long = 1L,
    socialService: SocialService = SocialService.GOOGLE,
    socialServiceUserId: String = "asdfadfad",
    linkedAt: AppDateTime = appDateTimeFixture(minute = 0)
): SocialConnection {
    return SocialConnection(
        socialConnectionId = socialConnectionId,
        userId = userId,
        socialService = socialService,
        socialServiceUserId = socialServiceUserId,
        linkedAt = linkedAt
    )
}
