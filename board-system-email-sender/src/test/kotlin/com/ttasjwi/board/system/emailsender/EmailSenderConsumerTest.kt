package com.ttasjwi.board.system.emailsender

import com.ttasjwi.board.system.common.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("EmailSenderConsumer: 애플리케이션 이벤트를 읽고, 이메일을 발송한다.")
class EmailSenderConsumerTest {

    private lateinit var emailSenderConsumer: EmailSenderConsumer
    private lateinit var emailSender: EmailSender
    private lateinit var messageResolver: MessageResolver

    @BeforeEach
    fun setup() {
        emailSender = mockk<EmailSender>()
        messageResolver = mockk<MessageResolver>()
        emailSenderConsumer = EmailSenderConsumer(
            messageResolver = messageResolver,
            emailSender = emailSender,
        )
    }

    @Nested
    @DisplayName("HandlerEmailVerificationStartedEvent: 이메일 인증 시작 이벤트 소비")
    inner class HandleEmailVerificationStartedEvent {

        @Test
        @DisplayName("이벤트를 읽어와서, 메시지를 구성하고 이메일을 발송한다.")
        fun testHandleEmailVerificationStartedEvent() {
            val event = EmailVerificationStartedEvent.create(
                email = "test@test.com",
                code = "1234",
                codeCreatedAt = appDateTimeFixture(),
                codeExpiresAt = appDateTimeFixture(minute = 30),
                locale = Locale.KOREAN,
                eventCreatedAt = appDateTimeFixture(),
            )
            val args = listOf(event.payload.code)

            val subject = "subject"
            val content = "content"
            every { messageResolver.resolve("EmailVerification.EmailSubject", event.payload.locale) } returns subject
            every { messageResolver.resolve("EmailVerification.EmailContent", event.payload.locale, args) } returns content
            every { emailSender.send(event.payload.email, subject, content) } just Runs

            emailSenderConsumer.handleEmailVerificationStartedEvent(event)

            verify(exactly = 1) { messageResolver.resolve("EmailVerification.EmailSubject", event.payload.locale) }
            verify(exactly = 1) { messageResolver.resolve("EmailVerification.EmailContent", event.payload.locale, args) }
            verify(exactly = 1) { emailSender.send(event.payload.email, subject, content) }
        }
    }

}
