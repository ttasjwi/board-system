package com.ttasjwi.board.system.domain.member.event

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

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
