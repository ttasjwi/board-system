package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.domain.member.service.EmailSender

class EmailSenderFixture : EmailSender {

    companion object {
        private val log = getLogger(EmailSenderFixture::class.java)
    }

    override fun send(address: String, subject: String, content: String) {
        log.info {
            """
            이메일 발송됨(도메인서비스 픽스쳐)!
            - email= $address
            - subject = $subject
            - content = $content
            -----------------------------------------------------------------------------------------------------------
        """.trimIndent()
        }
    }
}
