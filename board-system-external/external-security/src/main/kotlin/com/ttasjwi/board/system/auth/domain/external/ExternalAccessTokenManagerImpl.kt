package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.AuthMember
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class ExternalAccessTokenManagerImpl : ExternalAccessTokenManager {

    override fun generate(authMember: AuthMember, issuedAt: ZonedDateTime, expiresAt: ZonedDateTime): AccessToken {
        TODO("Not yet implemented")
    }

    override fun parse(tokenValue: String): AccessToken {
        TODO("Not yet implemented")
    }
}
