package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.service.EmailSender

class EmailSenderFixture : EmailSender {

    companion object {
        private val log = getLogger(EmailSenderFixture::class.java)
    }

    override fun send(address: Email, subject: String, content: String) {
        log.info{"""
            이메일 발송됨(도메인서비스 픽스쳐)!
            - email= ${address.value}
            - subject = $subject
            - content = $content
            -----------------------------------------------------------------------------------------------------------
        """.trimIndent()}
    }
}
