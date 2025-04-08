package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.external.AccessTokenGenerator
import com.ttasjwi.board.system.common.token.AccessToken
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime

@DomainService
internal class AccessTokenGeneratorImpl(
    private val accessTokenGenerator: AccessTokenGenerator
) : AccessTokenGenerator {

    override fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken {
        return accessTokenGenerator.generate(authMember, issuedAt, expiresAt)
    }
}
