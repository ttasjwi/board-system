package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest

interface OAuth2AuthorizationRequestPersistencePort {

    fun save(
        authorizationRequest: OAuth2AuthorizationRequest,
        expiresAt: AppDateTime,
    )

    fun read(state: String): OAuth2AuthorizationRequest?
    fun remove(state: String): OAuth2AuthorizationRequest?
}
