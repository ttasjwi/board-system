package com.ttasjwi.board.system.common.event

enum class EventType(val payloadClass: Class<out EventPayload>) {
    EMAIL_VERIFICATION_STARTED(EmailVerificationStartedEvent.EmailVerificationStartedEventPayload::class.java)
}
