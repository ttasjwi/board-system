package com.ttasjwi.board.system.emailsender

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

@DisplayName("EmailSender 단위 테스트")
class EmailSenderTest {
    private lateinit var emailSender: EmailSender
    private lateinit var javaMailSender: JavaMailSender

    @BeforeEach
    fun setup() {
        javaMailSender = mockk<JavaMailSender>()
        emailSender = EmailSender(javaMailSender)
    }

    @Test
    @DisplayName("javaMailSender 를 호출하는 지 테스트")
    fun test() {
        val address = "test@test.com"
        val subject = "test"
        val content = "test content"

        every { javaMailSender.send(any(SimpleMailMessage::class)) } just runs

        emailSender.send(address, subject, content)

        verify(exactly = 1) { javaMailSender.send(any(SimpleMailMessage::class))  }
    }
}
