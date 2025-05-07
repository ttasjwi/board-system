package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.model.SocialService
import com.ttasjwi.board.system.user.domain.port.SocialConnectionPersistencePort

class SocialConnectionPersistencePortFixture : SocialConnectionPersistencePort {

    private val storage: MutableMap<Long, SocialConnection> = mutableMapOf()

    override fun save(socialConnection: SocialConnection): SocialConnection {
        storage[socialConnection.socialConnectionId] = socialConnection
        return socialConnection
    }

    override fun read(socialService: SocialService, socialServiceUserId: String): SocialConnection? {
        return storage.values.firstOrNull {
            it.socialService == socialService && it.socialServiceUserId == socialServiceUserId
        }
    }
}
