package com.ttasjwi.board.system.email.domain.service

import com.ttasjwi.board.system.email.domain.model.Email

interface EmailSender {

    fun send(
        address: Email,
        subject: String,
        content: String
    ): Result<Unit>
}
