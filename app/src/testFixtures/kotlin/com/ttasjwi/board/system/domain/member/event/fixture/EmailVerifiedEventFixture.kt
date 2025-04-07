package com.ttasjwi.board.system.domain.member.event.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.member.event.EmailVerifiedEvent

fun emailVerifiedEventFixture(
    email: String = "hello@gmail.com",
    verifiedAt: AppDateTime = appDateTimeFixture(minute = 0),
    verificationExpiresAt: AppDateTime = verifiedAt.plusMinutes(30)
): EmailVerifiedEvent {
    return EmailVerifiedEvent(
        email = email,
        verifiedAt = verifiedAt,
        verificationExpiresAt = verificationExpiresAt,
    )
}
