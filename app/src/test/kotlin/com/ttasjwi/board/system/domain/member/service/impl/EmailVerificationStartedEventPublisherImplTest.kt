package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.domain.member.event.emailVerificationStartedEventFixture
import com.ttasjwi.board.system.domain.member.service.EmailSender
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.context.bean.override.mockito.MockitoBean

@DisplayName("EmailVerificationStartedEventPublisherImpl: 이메일인증 시작됨 이벤트 발행자")
class EmailVerificationStartedEventPublisherImplTest : IntegrationTest() {

    @MockitoBean
    private lateinit var emailSender: EmailSender

    @Test
    @DisplayName("이벤트 발행이 실행되는 지 확인")
    fun test() {
        val event = emailVerificationStartedEventFixture()
        emailVerificationStartedEventPublisher.publishEvent(event)
    }

}
