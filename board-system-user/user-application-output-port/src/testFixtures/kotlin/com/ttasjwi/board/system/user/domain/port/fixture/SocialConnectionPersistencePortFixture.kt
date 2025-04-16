package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.model.SocialServiceUser
import com.ttasjwi.board.system.user.domain.port.SocialConnectionPersistencePort

class SocialConnectionPersistencePortFixture : SocialConnectionPersistencePort {

    private val storage: MutableMap<Long, SocialConnection> = mutableMapOf()

    override fun save(socialConnection: SocialConnection): SocialConnection {
        storage[socialConnection.socialConnectionId] = socialConnection
        return socialConnection
    }

    override fun findBySocialServiceUserOrNull(socialServiceUser: SocialServiceUser): SocialConnection? {
        return storage.values.firstOrNull { it.socialServiceUser == socialServiceUser }
    }
}
