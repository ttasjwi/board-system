package com.ttasjwi.board.system.emailsender

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailSender(
    private val javaMailSender: JavaMailSender,
){

    fun send(address: String, subject: String, content: String) {
        val message = SimpleMailMessage()
        message.setTo(address)
        message.subject = subject
        message.text = content
        javaMailSender.send(message)
    }
}
