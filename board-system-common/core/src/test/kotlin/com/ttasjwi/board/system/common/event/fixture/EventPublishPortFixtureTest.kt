package com.ttasjwi.board.system.common.event.fixture

import com.ttasjwi.board.system.common.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.common.event.Event
import com.ttasjwi.board.system.common.event.EventPublishPort
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

@DisplayName("EventPublishPortFixture 테스트")
class EventPublishPortFixtureTest {

    private lateinit var eventPublishPortFixture: EventPublishPortFixture


    @BeforeEach
    fun setUp() {
        eventPublishPortFixture = EventPublishPortFixture()
    }

    @Test
    @DisplayName("그냥 실행만 된다.")
    fun justRunsTest() {
        val email = "hello@gmail.com"
        val code = "1234a"
        val codeCreatedAt = appDateTimeFixture()
        val codeExpiresAt = appDateTimeFixture(minute = 5)
        val locale = Locale.KOREAN

        // when
        val event = EmailVerificationStartedEvent.create(
            email = email,
            code = code,
            codeCreatedAt = codeCreatedAt,
            codeExpiresAt = codeExpiresAt,
            locale = locale,
            eventCreatedAt = codeCreatedAt
        )

        assertDoesNotThrow { eventPublishPortFixture.publish(event) }
    }
}
