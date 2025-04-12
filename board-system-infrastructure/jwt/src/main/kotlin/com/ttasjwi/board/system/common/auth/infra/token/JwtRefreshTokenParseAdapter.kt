package com.ttasjwi.board.system.common.auth.infra.token

import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.auth.RefreshTokenParsePort
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException

class JwtRefreshTokenParseAdapter(
    private val jwtDecoder: JwtDecoder
) : RefreshTokenParsePort {

    companion object {
        internal const val REFRESH_TOKEN_ID_CLAIM = "refreshTokenId"
        internal const val TOKEN_TYPE_CLAIM = "tokenType"
    }

    override fun parse(tokenValue: String): RefreshToken {
        val jwt: Jwt
        try {
            jwt = jwtDecoder.decode(tokenValue)
        } catch (e: JwtException) {
            throw InvalidRefreshTokenFormatException(e)
        }
        val tokenType = jwt.getClaim<String>(TOKEN_TYPE_CLAIM)
        val issuer = jwt.claims["iss"]
        if (tokenType != RefreshToken.VALID_TOKEN_TYPE || issuer != RefreshToken.VALID_ISSUER) {
            throw InvalidRefreshTokenFormatException()
        }
        return makeRefreshTokenFromJwt(jwt)
    }

    private fun makeRefreshTokenFromJwt(jwt: Jwt): RefreshToken {
        return RefreshToken.restore(
            memberId = jwt.subject.toLong(),
            refreshTokenId = jwt.getClaim(REFRESH_TOKEN_ID_CLAIM),
            tokenValue = jwt.tokenValue,
            issuedAt = jwt.issuedAt!!,
            expiresAt = jwt.expiresAt!!
        )
    }
}
