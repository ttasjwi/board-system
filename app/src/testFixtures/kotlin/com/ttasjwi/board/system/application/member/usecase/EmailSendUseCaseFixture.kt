package com.ttasjwi.board.system.application.member.usecase

import com.ttasjwi.board.system.global.logging.getLogger

class EmailSendUseCaseFixture : EmailSendUseCase {

    private val log = getLogger(EmailSendUseCaseFixture::class.java)

    override fun sendEmail(address: String, subject: String, content: String) {
        log.info {
            """
            이메일 발송됨(유즈케이스 픽스쳐)
            - email= $address
            - subject = $subject
            - content = $content
            -----------------------------------------------------------------------------------------------------------
        """.trimIndent()
        }
    }
}
