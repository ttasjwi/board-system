package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.port.OAuth2AuthorizationRequestPersistencePort

class OAuth2AuthorizationRequestPersistencePortFixture : OAuth2AuthorizationRequestPersistencePort {

    private val storage = mutableMapOf<String, OAuth2AuthorizationRequest>()

    override fun save(authorizationRequest: OAuth2AuthorizationRequest, expiresAt: AppDateTime) {
        storage[authorizationRequest.state] = authorizationRequest
    }

    override fun read(state: String): OAuth2AuthorizationRequest? {
        return storage[state]
    }

    override fun remove(state: String): OAuth2AuthorizationRequest? {
        val authorizationRequest = storage[state] ?: return null
        storage.remove(authorizationRequest.state)
        return authorizationRequest
    }
}
