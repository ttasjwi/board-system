package com.ttasjwi.board.system.common.auth.infra.token

import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.AccessTokenParsePort
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException

class JwtAccessTokenParseAdapter(
    private val jwtDecoder: JwtDecoder,
) : AccessTokenParsePort {

    companion object {
        internal const val TOKEN_TYPE_CLAIM = "tokenType"
        private const val ROLE_CLAIM = "role"
    }

    override fun parse(tokenValue: String): AccessToken {
        val jwt: Jwt
        try {
            jwt = jwtDecoder.decode(tokenValue)
        } catch (e: JwtException) {
            throw InvalidAccessTokenFormatException(e)
        }
        val tokenType = jwt.getClaim<String>(TOKEN_TYPE_CLAIM)
        val issuer = jwt.claims["iss"]
        if (tokenType != AccessToken.VALID_TOKEN_TYPE || issuer != AccessToken.VALID_ISSUER) {
            throw InvalidAccessTokenFormatException()
        }
        return makeAccessTokenFromJwt(jwt)
    }

    private fun makeAccessTokenFromJwt(jwt: Jwt): AccessToken {
        return AccessToken.restore(
            memberId = jwt.subject.toLong(),
            roleName = jwt.getClaim(ROLE_CLAIM),
            tokenValue = jwt.tokenValue,
            issuedAt = jwt.issuedAt!!,
            expiresAt = jwt.expiresAt!!
        )
    }
}
