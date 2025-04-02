package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.EmailVerification

fun emailVerificationFixtureNotVerified(
    email: String = "test@gmail.com",
    code: String = "1111",
    codeCreatedAt: AppDateTime = appDateTimeFixture(minute = 0),
    codeExpiresAt: AppDateTime = codeCreatedAt.plusMinutes(5),
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
    codeCreatedAt: AppDateTime = appDateTimeFixture(minute = 0),
    codeExpiresAt: AppDateTime = codeCreatedAt.plusMinutes(5),
    verifiedAt: AppDateTime = appDateTimeFixture(minute = 1),
    verificationExpiresAt: AppDateTime = verifiedAt.plusMinutes(30)
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
