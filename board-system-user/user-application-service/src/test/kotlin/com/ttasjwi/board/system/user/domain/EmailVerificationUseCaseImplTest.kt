package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.EmailVerification
import com.ttasjwi.board.system.user.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.user.domain.port.fixture.EmailVerificationPersistencePortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationUseCaseImpl 테스트")
class EmailVerificationUseCaseImplTest {

    private lateinit var useCase: EmailVerificationUseCase
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        timeManagerFixture = container.timeManagerFixture
        emailVerificationPersistencePortFixture = container.emailVerificationPersistencePortFixture
        useCase = container.emailVerificationUseCase
    }

    @Test
    @DisplayName("이메일 인증을 처리하고 그 결과를 반환한다.")
    fun test() {
        // given
        val savedEmailVerification = emailVerificationFixtureNotVerified(
            email = "hello@gmail.com",
            code = "1234",
            codeCreatedAt = appDateTimeFixture(minute = 0),
            codeExpiresAt = appDateTimeFixture(minute = 5),
        )
        emailVerificationPersistencePortFixture.save(savedEmailVerification, savedEmailVerification.codeExpiresAt)
        timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 3))

        val request = EmailVerificationRequest(
            email = savedEmailVerification.email,
            code = savedEmailVerification.code
        )

        // when
        val response = useCase.emailVerification(request)

        // then
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.verificationExpiresAt).isEqualTo(
            appDateTimeFixture(minute = 3).plusMinutes(EmailVerification.VERIFICATION_VALIDITY_MINUTE).toZonedDateTime()
        )
    }
}
