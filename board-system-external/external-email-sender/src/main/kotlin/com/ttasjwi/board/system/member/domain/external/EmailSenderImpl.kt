package com.ttasjwi.board.system.member.domain.external

import com.ttasjwi.board.system.core.annotation.component.AppComponent
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.service.EmailSender

@AppComponent
class EmailSenderImpl : EmailSender {

    override fun send(address: Email, subject: String, content: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}
