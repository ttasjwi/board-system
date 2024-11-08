package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.application.usecase.EmailSendUseCase
import com.ttasjwi.board.system.member.domain.service.EmailCreator
import com.ttasjwi.board.system.member.domain.service.EmailSender

@ApplicationService
internal class EmailSendApplicationService(
    private val emailCreator: EmailCreator,
    private val emailSender: EmailSender,
) : EmailSendUseCase {

    override fun sendEmail(address: String, subject: String, content: String) {
        val email = emailCreator.create(address).getOrThrow()
        emailSender.send(email, subject, content)
    }
}
