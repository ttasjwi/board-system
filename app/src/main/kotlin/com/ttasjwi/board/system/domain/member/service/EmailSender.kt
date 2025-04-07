package com.ttasjwi.board.system.domain.member.service

interface EmailSender {

    fun send(
        address: String,
        subject: String,
        content: String
    )
}
