package com.ttasjwi.board.system.member.domain.event.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import java.time.ZonedDateTime

fun emailVerificationStartedEventFixture(
    email: String = "hello@gmail.com",
    code: String = "code12345",
    codeCreatedAt: ZonedDateTime = timeFixture(minute = 0),
    codeExpiresAt: ZonedDateTime = codeCreatedAt.plusMinutes(5),
): EmailVerificationStartedEvent {
    return EmailVerificationStartedEvent(
        email = email,
        code = code,
        codeCreatedAt = codeCreatedAt,
        codeExpiresAt = codeExpiresAt,
    )
}
