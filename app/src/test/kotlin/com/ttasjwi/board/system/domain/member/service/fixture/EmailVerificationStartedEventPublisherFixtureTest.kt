package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.domain.member.service.fixture.EmailVerificationStartedEventPublisherFixture
import com.ttasjwi.board.system.domain.member.event.fixture.emailVerificationStartedEventFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationStartedEventPublisher 픽스쳐 테스트")
class EmailVerificationStartedEventPublisherFixtureTest {

    private lateinit var eventPublisherFixture: EmailVerificationStartedEventPublisherFixture

    @BeforeEach
    fun setup() {
        eventPublisherFixture = EmailVerificationStartedEventPublisherFixture()
    }

    @Test
    @DisplayName("호출이 잘 되는 지 테스트")
    fun test() {
        val event = emailVerificationStartedEventFixture()
        eventPublisherFixture.publishEvent(event)
    }
}
