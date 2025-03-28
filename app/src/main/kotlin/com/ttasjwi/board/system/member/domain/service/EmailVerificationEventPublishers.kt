package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.common.domain.event.DomainEventPublisher
import com.ttasjwi.board.system.member.domain.event.EmailVerificationStartedEvent

interface EmailVerificationStartedEventPublisher : DomainEventPublisher<EmailVerificationStartedEvent>
