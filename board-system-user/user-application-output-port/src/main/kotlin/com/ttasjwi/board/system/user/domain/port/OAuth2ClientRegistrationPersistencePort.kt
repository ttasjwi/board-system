package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration

interface OAuth2ClientRegistrationPersistencePort {
    fun findById(registrationId: String): OAuth2ClientRegistration?
}
