package com.ttasjwi.board.system.member.domain.event.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.event.EmailVerifiedEvent
import java.time.ZonedDateTime

fun emailVerifiedEventFixture(
    email: String = "hello@gmail.com",
    verifiedAt: ZonedDateTime = timeFixture(minute = 0),
    verificationExpiresAt: ZonedDateTime = verifiedAt.plusMinutes(30)
): EmailVerifiedEvent {
    return EmailVerifiedEvent(
        email = email,
        verifiedAt = verifiedAt,
        verificationExpiresAt = verificationExpiresAt,
    )
}
