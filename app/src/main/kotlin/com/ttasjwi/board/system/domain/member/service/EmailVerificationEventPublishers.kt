package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.event.EmailVerificationStartedEvent
import com.ttasjwi.board.system.global.domain.DomainEventPublisher

interface EmailVerificationStartedEventPublisher : DomainEventPublisher<EmailVerificationStartedEvent>
