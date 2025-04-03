package com.ttasjwi.board.system.auth.domain.external.impl

import com.ttasjwi.board.system.auth.domain.exception.InvalidAccessTokenFormatException
import com.ttasjwi.board.system.auth.domain.external.ExternalAccessTokenManager
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Component

@Component
class ExternalAccessTokenManagerImpl(
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder
) : ExternalAccessTokenManager {

    companion object {
        private const val TOKEN_TYPE_CLAIM = "tokenType"
        private const val TOKEN_TYPE_VALUE = "accessToken"
        private const val ROLE_CLAIM = "role"
        private const val ISSUER_VALUE = "board-system"
    }

    override fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken {
        val jwt = makeJwt(authMember, issuedAt, expiresAt)
        return makeAccessTokenFromJwt(jwt)
    }

    override fun parse(tokenValue: String): AccessToken {
        val jwt: Jwt
        try {
            jwt = jwtDecoder.decode(tokenValue)
        } catch (e: JwtException) {
            throw InvalidAccessTokenFormatException(e)
        }
        val tokenType = jwt.getClaim<String>(TOKEN_TYPE_CLAIM)

        if (tokenType != TOKEN_TYPE_VALUE) {
            throw InvalidAccessTokenFormatException()
        }
        return makeAccessTokenFromJwt(jwt)
    }

    private fun makeJwt(loginMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): Jwt {
        val jwsHeader = makeHeader()
        val jwtClaimsSet = makeClaimSet(loginMember, issuedAt, expiresAt)
        val params = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)
        return jwtEncoder.encode(params)
    }

    private fun makeHeader(): JwsHeader {
        val jwsAlgorithm = SignatureAlgorithm.RS256
        val jwsHeaderBuilder = JwsHeader.with(jwsAlgorithm)
        return jwsHeaderBuilder.build()
    }

    private fun makeClaimSet(loginMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): JwtClaimsSet {
        return JwtClaimsSet.builder()
            .subject(loginMember.memberId.toString())
            .issuer(ISSUER_VALUE)
            .issuedAt(issuedAt.toInstant())
            .expiresAt(expiresAt.toInstant())
            .claim(TOKEN_TYPE_CLAIM, TOKEN_TYPE_VALUE)
            .claim(ROLE_CLAIM, loginMember.role.name)
            .build()
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
