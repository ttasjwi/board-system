package com.ttasjwi.board.system.domain.member.external.impl

import com.ttasjwi.board.system.domain.member.service.EmailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailSenderImpl(
    private val javaMailSender: JavaMailSender
) : EmailSender {

    override fun send(address: String, subject: String, content: String) {
        val message = SimpleMailMessage()
        message.subject = subject
        message.setTo(address)
        message.text = content

        javaMailSender.send(message)
    }
}
