package com.ttasjwi.board.system.auth.domain.external.impl

import com.ttasjwi.board.system.auth.domain.external.AccessTokenGenerator
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.token.AccessToken
import com.ttasjwi.board.system.common.token.AccessTokenParser

/**
 * 액세스토큰 생성 및 파싱을 모두 담당할 수 있는 AccessTokenManager
 */
class JwtAccessTokenManager(
    private val jwtAccessTokenGenerator: JwtAccessTokenGenerator,
    private val jwtAccessTokenParser: JwtAccessTokenParser,
) : AccessTokenGenerator, AccessTokenParser {

    override fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken {
        return jwtAccessTokenGenerator.generate(authMember, issuedAt, expiresAt)
    }

    override fun parse(tokenValue: String): AccessToken {
        return jwtAccessTokenParser.parse(tokenValue)
    }
}
