package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.service.EmailVerificationStartedEventPublisher

class EmailVerificationStartedEventPublisherFixture : EmailVerificationStartedEventPublisher {

    companion object {
        private val log = getLogger(EmailVerificationStartedEventPublisherFixture::class.java)
    }

    override fun publishEvent(event: EmailVerificationStartedEvent) {
        log.info{ "(픽스쳐) '이메일인증 시작됨' 이벤트 발행 (email = ${event.data.email})" }
    }
}
