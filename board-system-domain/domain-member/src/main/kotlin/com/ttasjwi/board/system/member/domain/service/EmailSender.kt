package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Email

interface EmailSender {

    fun send(
        address: Email,
        subject: String,
        content: String
    )
}
