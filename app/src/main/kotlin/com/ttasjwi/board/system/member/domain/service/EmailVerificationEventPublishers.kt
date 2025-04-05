package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.global.domain.DomainEventPublisher
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent

interface EmailVerificationStartedEventPublisher : DomainEventPublisher<EmailVerificationStartedEvent>
