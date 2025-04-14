package com.ttasjwi.board.system.member.domain.port.fixture

import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.port.SocialConnectionPersistencePort

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
