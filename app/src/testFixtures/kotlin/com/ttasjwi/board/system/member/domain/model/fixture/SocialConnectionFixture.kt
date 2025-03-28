package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialService
import java.time.ZonedDateTime

fun socialConnectionFixtureSaved(
    id: Long = 1L,
    memberId: Long = 1L,
    socialService: SocialService = SocialService.GOOGLE,
    socialServiceUserId: String = "asdfadfad",
    linkedAt: ZonedDateTime = timeFixture(minute = 0)
): SocialConnection {
    return SocialConnection(
        id = socialConnectionIdFixture(id),
        memberId = memberIdFixture(memberId),
        socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
        linkedAt = linkedAt
    )
}

fun socialConnectionFixtureNotSaved(
    memberId: Long = 1L,
    socialService: SocialService = SocialService.GOOGLE,
    socialServiceUserId: String = "asdfadfad",
    linkedAt: ZonedDateTime = timeFixture(minute = 0)
): SocialConnection {
    return SocialConnection(
        memberId = memberIdFixture(memberId),
        socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
        linkedAt = linkedAt
    )
}
