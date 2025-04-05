package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.application.dto.EmailVerificationStartCommand
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationEventCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailVerificationStorageFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("EmailVerificationStartProcessor: 이메일 인증 시작에 필요한 사전 작업 처리를 담당하는 처리자")
class EmailVerificationStartProcessorTest {

    private lateinit var processor: EmailVerificationStartProcessor
    private lateinit var emailVerificationCreatorFixture: EmailVerificationCreatorFixture
    private lateinit var emailVerificationStorageFixture: EmailVerificationStorageFixture
    private lateinit var emailVerificationEventCreatorFixture: EmailVerificationEventCreatorFixture

    @BeforeEach
    fun setup() {
        emailVerificationCreatorFixture = EmailVerificationCreatorFixture()
        emailVerificationStorageFixture = EmailVerificationStorageFixture()
        emailVerificationEventCreatorFixture = EmailVerificationEventCreatorFixture()
        processor = EmailVerificationStartProcessor(
            emailVerificationCreator = emailVerificationCreatorFixture,
            emailVerificationAppender = emailVerificationStorageFixture,
            emailVerificationEventCreator = emailVerificationEventCreatorFixture,
        )
    }

    @Test
    @DisplayName("이메일인증을 생성하고, 저장하고, 이벤트를 생성하여 반환한다.")
    fun testSuccess() {

        // given
        val command = EmailVerificationStartCommand(
            email = "hell@gmail.com",
            currenTime = appDateTimeFixture(minute = 3),
            locale = Locale.KOREAN
        )

        // when
        val event = processor.startEmailVerification(command)

        // then
        val findEmailVerification = emailVerificationStorageFixture.findByEmailOrNull(command.email)!!
        val data = event.data

        assertThat(findEmailVerification.email).isEqualTo(data.email)
        assertThat(findEmailVerification.code).isEqualTo("code")
        assertThat(findEmailVerification.codeCreatedAt).isEqualTo(command.currenTime)
        assertThat(findEmailVerification.codeExpiresAt).isEqualTo(command.currenTime.plusMinutes(5))
        assertThat(findEmailVerification.verifiedAt).isNull()
        assertThat(findEmailVerification.verificationExpiresAt).isNull()

        assertThat(event.occurredAt).isEqualTo(findEmailVerification.codeCreatedAt)
        assertThat(data.email).isEqualTo(findEmailVerification.email)
        assertThat(data.code).isEqualTo(findEmailVerification.code)
        assertThat(data.codeCreatedAt).isEqualTo(findEmailVerification.codeCreatedAt)
        assertThat(data.codeExpiresAt).isEqualTo(findEmailVerification.codeExpiresAt)
    }

}
