package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.core.annotation.component.DomainService
import java.time.ZonedDateTime

@DomainService
internal class AccessTokenManagerImpl : AccessTokenManager {

    override fun generate(authMember: AuthMember, issuedAt: ZonedDateTime): AccessToken {
        TODO("Not yet implemented")
    }

    override fun parse(tokenValue: String): AccessToken {
        TODO("Not yet implemented")
    }
}
