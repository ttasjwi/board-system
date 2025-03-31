package com.ttasjwi.board.system.member.domain.external.impl

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.service.EmailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailSenderImpl(
    private val javaMailSender: JavaMailSender
) : EmailSender {

    override fun send(address: Email, subject: String, content: String) {
        val message = SimpleMailMessage()
        message.subject = subject
        message.setTo(address.value)
        message.text = content

        javaMailSender.send(message)
    }
}
