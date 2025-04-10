package com.ttasjwi.board.system.common.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EventType 테스트")
class EventTypeTest {

    @Test
    @DisplayName("'EMAIL_VERIFICATION_STARTED' EventType 의 Payload 클래스 테스트")
    fun emailVerificationStartedTest() {
        val eventType = EventType.EMAIL_VERIFICATION_STARTED

        val payloadClass = eventType.payloadClass

        assertThat(payloadClass).isEqualTo(EmailVerificationStartedEvent.EmailVerificationStartedEventPayload::class.java)
    }
}
