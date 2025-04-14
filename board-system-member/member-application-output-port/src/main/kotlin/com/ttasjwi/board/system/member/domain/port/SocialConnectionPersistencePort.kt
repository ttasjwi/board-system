package com.ttasjwi.board.system.member.domain.port

import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser

interface SocialConnectionPersistencePort {

    fun save(socialConnection: SocialConnection): SocialConnection
    fun findBySocialServiceUserOrNull(socialServiceUser: SocialServiceUser): SocialConnection?
}
