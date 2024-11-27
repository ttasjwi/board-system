package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialConnectionId
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.service.SocialConnectionStorage
import java.util.concurrent.atomic.AtomicLong

class SocialConnectionStorageFixture : SocialConnectionStorage {

    private val storage: MutableMap<SocialConnectionId, SocialConnection> = mutableMapOf()
    private val sequence = AtomicLong(0)

    override fun save(socialConnection: SocialConnection): SocialConnection {
        val nextId = SocialConnectionId.restore(sequence.incrementAndGet())
        socialConnection.initId(nextId)
        storage[nextId] = socialConnection
        return socialConnection
    }

    override fun findBySocialServiceUserOrNull(socialServiceUser: SocialServiceUser): SocialConnection? {
        return storage.values.firstOrNull{ it.socialServiceUser == socialServiceUser }
    }
}
