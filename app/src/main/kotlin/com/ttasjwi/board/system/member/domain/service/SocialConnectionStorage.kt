package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser

interface SocialConnectionStorage {

    fun save(socialConnection: SocialConnection): SocialConnection
    fun findBySocialServiceUserOrNull(socialServiceUser: SocialServiceUser): SocialConnection?
}
