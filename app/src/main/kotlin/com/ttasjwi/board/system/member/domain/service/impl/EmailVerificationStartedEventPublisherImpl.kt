package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.member.domain.service.EmailVerificationStartedEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EmailVerificationStartedEventPublisherImpl(
    private val applicationEventPublisher: ApplicationEventPublisher
) : EmailVerificationStartedEventPublisher {

    companion object {
        private val log = getLogger(EmailVerificationStartedEventPublisherImpl::class.java)
    }

    override fun publishEvent(event: EmailVerificationStartedEvent) {
        applicationEventPublisher.publishEvent(event)
        log.info { "이메일 인증 시작됨 내부이벤트 발행" }
    }
}
