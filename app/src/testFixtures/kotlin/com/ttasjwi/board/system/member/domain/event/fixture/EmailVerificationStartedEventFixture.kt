package com.ttasjwi.board.system.member.domain.event.fixture

import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import java.time.ZonedDateTime
import java.util.*

fun emailVerificationStartedEventFixture(
    email: String = "hello@gmail.com",
    code: String = "code12345",
    codeCreatedAt: ZonedDateTime = timeFixture(minute = 0),
    codeExpiresAt: ZonedDateTime = codeCreatedAt.plusMinutes(5),
    locale: Locale = Locale.KOREAN,
): EmailVerificationStartedEvent {
    return EmailVerificationStartedEvent(
        email = email,
        code = code,
        codeCreatedAt = codeCreatedAt,
        codeExpiresAt = codeExpiresAt,
        locale = locale,
    )
}
