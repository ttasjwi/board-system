package com.ttasjwi.board.system.domain.member.event

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import java.util.*

fun emailVerificationStartedEventFixture(
    email: String = "hello@gmail.com",
    code: String = "code12345",
    codeCreatedAt: AppDateTime = appDateTimeFixture(minute = 0),
    codeExpiresAt: AppDateTime = codeCreatedAt.plusMinutes(5),
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
