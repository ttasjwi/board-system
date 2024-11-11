package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

@DomainService
internal class RefreshTokenManagerImpl : RefreshTokenManager {

    override fun generate(memberId: MemberId, issuedAt: ZonedDateTime): RefreshToken {
        TODO("Not yet implemented")
    }

    override fun parse(tokenValue: String): RefreshToken {
        TODO("Not yet implemented")
    }
}
