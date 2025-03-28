package com.ttasjwi.board.system.member.application.usecase

interface EmailSendUseCase {
    fun sendEmail(address: String, subject: String, content: String)
}
