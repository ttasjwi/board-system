package com.ttasjwi.board.system.user.infra.oauth2

import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.port.OAuth2ClientRegistrationPersistencePort

class MemoryOAuth2ClientRegistrationPersistenceAdapter(
    private val storage: Map<String, OAuth2ClientRegistration>
) : OAuth2ClientRegistrationPersistencePort {

    override fun findById(registrationId: String): OAuth2ClientRegistration? {
        return storage[registrationId]
    }
}
