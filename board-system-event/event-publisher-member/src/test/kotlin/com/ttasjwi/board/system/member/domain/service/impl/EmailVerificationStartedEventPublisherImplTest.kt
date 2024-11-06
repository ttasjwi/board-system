package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.member.domain.event.fixture.emailVerificationStartedEventFixture
import com.ttasjwi.board.system.member.domain.service.EmailVerificationStartedEventPublisher
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile

@SpringBootTest
@Profile(value = ["test"])
@DisplayName("EmailVerificationStartedEventPublisherImpl: 이메일인증 시작됨 이벤트 발행자")
class EmailVerificationStartedEventPublisherImplTest @Autowired constructor(
    private val emailVerificationStartedEventPublisher: EmailVerificationStartedEventPublisher
) {

    @Test
    @DisplayName("이벤트 발행이 실행되는 지 확인")
    fun test() {
        val event = emailVerificationStartedEventFixture()
        emailVerificationStartedEventPublisher.publishEvent(event)
    }

}
