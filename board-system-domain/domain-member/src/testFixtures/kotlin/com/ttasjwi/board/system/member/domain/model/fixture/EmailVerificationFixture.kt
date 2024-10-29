package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.time.ZonedDateTime

fun emailVerificationFixtureNotVerified(
    email: String = "test@gmail.com",
    code: String = "1111",
    codeCreatedAt: ZonedDateTime = timeFixture(minute = 0),
    codeExpiresAt: ZonedDateTime = codeCreatedAt.plusMinutes(5),
): EmailVerification {
    return EmailVerification(
        email = emailFixture(email),
        code = code,
        codeCreatedAt = codeCreatedAt,
        codeExpiresAt = codeExpiresAt,
        verifiedAt = null,
        verificationExpiresAt = null
    )
}

fun emailVerificationFixtureVerified(
    email: String = "test@gmail.com",
    code: String = "1111",
    codeCreatedAt: ZonedDateTime = timeFixture(minute = 0),
    codeExpiresAt: ZonedDateTime = codeCreatedAt.plusMinutes(5),
    verifiedAt: ZonedDateTime = timeFixture(minute = 1),
    verificationExpiresAt: ZonedDateTime = verifiedAt.plusMinutes(30)
): EmailVerification {
    return EmailVerification(
        email = emailFixture(email),
        code = code,
        codeCreatedAt = codeCreatedAt,
        codeExpiresAt = codeExpiresAt,
        verifiedAt = verifiedAt,
        verificationExpiresAt = verificationExpiresAt
    )
}
