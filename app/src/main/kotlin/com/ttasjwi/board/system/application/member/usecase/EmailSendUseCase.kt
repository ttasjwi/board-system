package com.ttasjwi.board.system.application.member.usecase

interface EmailSendUseCase {
    fun sendEmail(address: String, subject: String, content: String)
}
