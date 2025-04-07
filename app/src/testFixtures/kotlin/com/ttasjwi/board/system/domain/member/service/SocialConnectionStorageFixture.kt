package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.SocialConnection
import com.ttasjwi.board.system.domain.member.model.SocialServiceUser

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
