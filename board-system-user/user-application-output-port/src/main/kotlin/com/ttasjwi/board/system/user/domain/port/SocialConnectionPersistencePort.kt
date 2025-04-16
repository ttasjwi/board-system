package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.model.SocialServiceUser

interface SocialConnectionPersistencePort {

    fun save(socialConnection: SocialConnection): SocialConnection
    fun findBySocialServiceUserOrNull(socialServiceUser: SocialServiceUser): SocialConnection?
}
