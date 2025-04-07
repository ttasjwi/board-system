package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.SocialConnection
import com.ttasjwi.board.system.domain.member.model.SocialServiceUser

interface SocialConnectionStorage {

    fun save(socialConnection: SocialConnection): SocialConnection
    fun findBySocialServiceUserOrNull(socialServiceUser: SocialServiceUser): SocialConnection?
}
