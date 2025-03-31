package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.service.SocialConnectionStorage

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
