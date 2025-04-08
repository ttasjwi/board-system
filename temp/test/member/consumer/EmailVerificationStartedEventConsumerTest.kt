package com.ttasjwi.board.system.member.consumer

import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.usecase.fixture.EmailSendUseCaseFixture
import com.ttasjwi.board.system.member.domain.event.fixture.emailVerificationStartedEventFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class EmailVerificationStartedEventConsumerTest {

    private lateinit var emailVerificationStartedEventConsumer: EmailVerificationStartedEventConsumer

    @BeforeEach
    fun setup() {
        emailVerificationStartedEventConsumer = EmailVerificationStartedEventConsumer(
            emailSendUseCase = EmailSendUseCaseFixture(),
            messageResolver = MessageResolverFixture()
        )
    }

    @Test
    @DisplayName("동작 테스트")
    fun test() {
        val event = emailVerificationStartedEventFixture(
            email = "jello@gmail.com",
            code = "code"
        )
        emailVerificationStartedEventConsumer.handleEmailVerificationStartedEvent(event)
    }
}
