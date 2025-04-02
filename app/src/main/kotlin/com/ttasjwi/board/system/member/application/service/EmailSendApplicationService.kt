package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.member.application.usecase.EmailSendUseCase
import com.ttasjwi.board.system.member.domain.policy.EmailFormatPolicy
import com.ttasjwi.board.system.member.domain.service.EmailSender
import org.springframework.stereotype.Service

@Service
internal class EmailSendApplicationService(
    private val emailFormatPolicy: EmailFormatPolicy,
    private val emailSender: EmailSender,
) : EmailSendUseCase {

    override fun sendEmail(address: String, subject: String, content: String) {
        val email = emailFormatPolicy.validate(address).getOrThrow()
        emailSender.send(email, subject, content)
    }
}
