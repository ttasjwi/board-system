package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.external.ExternalAccessTokenManager
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import java.time.ZonedDateTime

@DomainService
internal class AccessTokenManagerImpl(
    private val externalAccessTokenManager: ExternalAccessTokenManager
) : AccessTokenManager {

    override fun generate(authMember: AuthMember, issuedAt: ZonedDateTime): AccessToken {
        val expiresAt = issuedAt.plusMinutes(AccessToken.VALIDITY_MINUTE)
        return externalAccessTokenManager.generate(authMember, issuedAt, expiresAt)
    }

    override fun parse(tokenValue: String): AccessToken {
        return externalAccessTokenManager.parse(tokenValue)
    }

    override fun checkCurrentlyValid(accessToken: AccessToken, currentTime: ZonedDateTime) {
        accessToken.checkCurrentlyValid(currentTime)
    }
}
