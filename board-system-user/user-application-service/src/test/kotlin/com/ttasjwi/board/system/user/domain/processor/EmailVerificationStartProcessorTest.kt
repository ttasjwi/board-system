package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.event.fixture.EventPublishPortFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.EmailVerificationStartCommand
import com.ttasjwi.board.system.user.domain.model.EmailVerification
import com.ttasjwi.board.system.user.domain.port.fixture.EmailVerificationPersistencePortFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("EmailVerificationStartProcessor: 이메일 인증 시작에 필요한 사전 작업 처리를 담당하는 처리자")
class EmailVerificationStartProcessorTest {

    private lateinit var processor: EmailVerificationStartProcessor

    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture
    private lateinit var eventPublishPortFixture: EventPublishPortFixture

    @BeforeEach
    fun setup() {
        emailVerificationPersistencePortFixture = EmailVerificationPersistencePortFixture()
        eventPublishPortFixture = EventPublishPortFixture()
        processor = EmailVerificationStartProcessor(
            emailVerificationPersistencePort = emailVerificationPersistencePortFixture,
            eventPublishPort = eventPublishPortFixture
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
        val createdEmailVerification = processor.startEmailVerification(command)

        // then
        val findEmailVerification = emailVerificationPersistencePortFixture.findByEmailOrNull(command.email)!!

        assertThat(createdEmailVerification.email).isEqualTo(command.email)
        assertThat(createdEmailVerification.code).isNotNull()
        assertThat(createdEmailVerification.code.length).isEqualTo(EmailVerification.CODE_LENGTH)
        assertThat(createdEmailVerification.codeCreatedAt).isEqualTo(command.currenTime)
        assertThat(createdEmailVerification.codeExpiresAt).isEqualTo(command.currenTime.plusMinutes(EmailVerification.CODE_VALIDITY_MINUTE))
        assertThat(createdEmailVerification.verifiedAt).isNull()
        assertThat(createdEmailVerification.verificationExpiresAt).isNull()

        assertThat(findEmailVerification.email).isEqualTo(createdEmailVerification.email)
        assertThat(findEmailVerification.code).isEqualTo(createdEmailVerification.code)
        assertThat(findEmailVerification.codeCreatedAt).isEqualTo(createdEmailVerification.codeCreatedAt)
        assertThat(findEmailVerification.codeExpiresAt).isEqualTo(createdEmailVerification.codeExpiresAt)
        assertThat(findEmailVerification.verifiedAt).isEqualTo(findEmailVerification.verifiedAt)
        assertThat(findEmailVerification.verificationExpiresAt).isEqualTo(findEmailVerification.verificationExpiresAt)
    }
}
