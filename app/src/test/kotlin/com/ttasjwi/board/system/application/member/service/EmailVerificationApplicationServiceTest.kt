package com.ttasjwi.board.system.application.member.service

import com.ttasjwi.board.system.application.member.mapper.EmailVerificationCommandMapper
import com.ttasjwi.board.system.application.member.processor.EmailVerificationProcessor
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.global.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.service.fixture.EmailManagerFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationEventCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationHandlerFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationStorageFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationApplicationService 테스트")
class EmailVerificationApplicationServiceTest {

    private lateinit var emailVerificationApplicationService: EmailVerificationApplicationService
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var emailVerificationStorageFixture: EmailVerificationStorageFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        emailVerificationStorageFixture = EmailVerificationStorageFixture()
        emailVerificationApplicationService = EmailVerificationApplicationService(
            commandMapper = EmailVerificationCommandMapper(
                emailManager = EmailManagerFixture(),
                timeManager = timeManagerFixture
            ),
            processor = EmailVerificationProcessor(
                emailVerificationFinder = emailVerificationStorageFixture,
                emailVerificationHandler = EmailVerificationHandlerFixture(),
                emailVerificationAppender = emailVerificationStorageFixture,
                emailVerificationEventCreator = EmailVerificationEventCreatorFixture()
            ),
        )
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
        emailVerificationStorageFixture.append(savedEmailVerification, savedEmailVerification.codeExpiresAt)
        timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 3))

        val request = EmailVerificationRequest(
            email = savedEmailVerification.email,
            code = savedEmailVerification.code
        )

        // when
        val response = emailVerificationApplicationService.emailVerification(request)

        // then
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.verificationExpiresAt).isEqualTo(
            appDateTimeFixture(minute = 3).plusMinutes(
                EmailVerificationHandlerFixture.VERIFICATION_VALIDITY_MINUTE
            ).toZonedDateTime()
        )
    }

}
