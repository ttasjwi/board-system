package com.ttasjwi.board.system.common.websupport.auth.token

import com.ttasjwi.board.system.common.token.AccessToken
import com.ttasjwi.board.system.common.token.AccessTokenParser
import com.ttasjwi.board.system.common.token.InvalidAccessTokenFormatException
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException

class JwtAccessTokenParser(
    private val jwtDecoder: JwtDecoder,
) : AccessTokenParser {

    companion object {
        private const val TOKEN_TYPE_CLAIM = "tokenType"
        private const val TOKEN_TYPE_VALUE = "AccessToken"
        private const val ROLE_CLAIM = "role"
        private const val ISSUER_VALUE = "BoardSystem"
    }

    override fun parse(tokenValue: String): AccessToken {
        val jwt: Jwt
        try {
            jwt = jwtDecoder.decode(tokenValue)
        } catch (e: JwtException) {
            throw InvalidAccessTokenFormatException(e)
        }
        val tokenType = jwt.getClaim<String>(TOKEN_TYPE_CLAIM)
        val issuer = jwt.issuer.toString()
        if (tokenType != TOKEN_TYPE_VALUE || issuer != ISSUER_VALUE) {
            throw InvalidAccessTokenFormatException()
        }
        return makeAccessTokenFromJwt(jwt)
    }

    private fun makeAccessTokenFromJwt(jwt: Jwt): AccessToken {
        return AccessToken.restore(
            memberId = jwt.subject.toLong(),
            roleName = jwt.getClaim(ROLE_CLAIM),
            tokenType = jwt.getClaim(TOKEN_TYPE_CLAIM),
            tokenValue = jwt.tokenValue,
            issuer = jwt.issuer.toString(),
            issuedAt = jwt.issuedAt!!,
            expiresAt = jwt.expiresAt!!
        )
    }
}
