package com.ttasjwi.board.system.common.infra.eventpublisher

import com.ttasjwi.board.system.common.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@DisplayName("EventPublisher 테스트")
class EventPublisherTest {

    private lateinit var eventPublisher: EventPublisher
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @BeforeEach
    fun setup() {
        applicationEventPublisher = mockk<ApplicationEventPublisher>()
        eventPublisher = EventPublisher(applicationEventPublisher)
    }

    @Test
    @DisplayName("ApplicationEventPublisher 를 호출하는 지 테스트")
    fun test() {
        // given
        val email = "hello@gmail.com"
        val code = "1234a"
        val codeCreatedAt = appDateTimeFixture()
        val codeExpiresAt = appDateTimeFixture(minute = 5)
        val locale = Locale.KOREAN

        val event = EmailVerificationStartedEvent.create(
            email = email,
            code = code,
            codeCreatedAt = codeCreatedAt,
            codeExpiresAt = codeExpiresAt,
            locale = locale,
            eventCreatedAt = codeCreatedAt
        )

        every { applicationEventPublisher.publishEvent(event) } just runs

        // when
        eventPublisher.publish(event)

        // then
        verify(exactly = 1) { applicationEventPublisher.publishEvent(event) }
    }
}
