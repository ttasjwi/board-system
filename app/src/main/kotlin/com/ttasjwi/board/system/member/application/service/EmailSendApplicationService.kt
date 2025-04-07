package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.domain.member.service.EmailManager
import com.ttasjwi.board.system.domain.member.service.EmailSender
import com.ttasjwi.board.system.member.application.usecase.EmailSendUseCase
import org.springframework.stereotype.Service

@Service
internal class EmailSendApplicationService(
    private val emailManager: EmailManager,
    private val emailSender: EmailSender,
) : EmailSendUseCase {

    override fun sendEmail(address: String, subject: String, content: String) {
        val email = emailManager.validate(address).getOrThrow()
        emailSender.send(email, subject, content)
    }
}
