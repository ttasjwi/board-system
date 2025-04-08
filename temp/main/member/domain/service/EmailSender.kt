package com.ttasjwi.board.system.member.domain.service

interface EmailSender {

    fun send(
        address: String,
        subject: String,
        content: String
    )
}
