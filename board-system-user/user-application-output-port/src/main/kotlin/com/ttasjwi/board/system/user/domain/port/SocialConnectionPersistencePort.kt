package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.model.SocialService

interface SocialConnectionPersistencePort {

    fun save(socialConnection: SocialConnection): SocialConnection
    fun read(socialService: SocialService, socialServiceUserId: String): SocialConnection?
}
