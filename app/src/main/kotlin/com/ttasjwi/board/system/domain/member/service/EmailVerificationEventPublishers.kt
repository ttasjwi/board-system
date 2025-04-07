package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.common.domain.event.DomainEventPublisher
import com.ttasjwi.board.system.domain.member.event.EmailVerificationStartedEvent

interface EmailVerificationStartedEventPublisher : DomainEventPublisher<EmailVerificationStartedEvent>
