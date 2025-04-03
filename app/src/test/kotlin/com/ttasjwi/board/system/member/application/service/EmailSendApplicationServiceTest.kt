package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.member.domain.service.fixture.EmailManagerFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailSenderFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailSendApplicationService 테스트")
class EmailSendApplicationServiceTest {

    private lateinit var emailSendApplicationService: EmailSendApplicationService

    @BeforeEach
    fun setup() {
        emailSendApplicationService = EmailSendApplicationService(
            emailManager = EmailManagerFixture(),
            emailSender = EmailSenderFixture()
        )
    }

    @Test
    @DisplayName("sendEmail : 도메인서비스를 통해 이메일 발송을 위임")
    fun test() {
        val email = "hello@gmail.com"
        val subject = "제목"
        val content = "본문"
        emailSendApplicationService.sendEmail(email, subject, content)
    }
}
