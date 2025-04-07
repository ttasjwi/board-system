package com.ttasjwi.board.system.domain.member.service.fixture

import com.ttasjwi.board.system.domain.member.model.SocialConnection
import com.ttasjwi.board.system.domain.member.model.SocialServiceUser
import com.ttasjwi.board.system.domain.member.service.SocialConnectionStorage

class SocialConnectionStorageFixture : SocialConnectionStorage {

    private val storage: MutableMap<Long, SocialConnection> = mutableMapOf()

    override fun save(socialConnection: SocialConnection): SocialConnection {
        storage[socialConnection.id] = socialConnection
        return socialConnection
    }

    override fun findBySocialServiceUserOrNull(socialServiceUser: SocialServiceUser): SocialConnection? {
        return storage.values.firstOrNull { it.socialServiceUser == socialServiceUser }
    }
}
