package com.ttasjwi.board.system.common.auth.infra.token

import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.AccessTokenGeneratePort
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.time.AppDateTime
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.*

class JwtAccessTokenGenerateAdapter(
    private val jwtEncoder: JwtEncoder
) : AccessTokenGeneratePort {

    companion object {
        private const val TOKEN_TYPE_CLAIM = "tokenType"
        private const val ROLE_CLAIM = "role"
    }

    override fun generate(authUser: AuthUser, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken {
        val jwt = makeJwt(authUser, issuedAt, expiresAt)
        return makeAccessTokenFromJwt(jwt)
    }

    private fun makeJwt(authUser: AuthUser, issuedAt: AppDateTime, expiresAt: AppDateTime): Jwt {
        val jwsHeader = makeHeader()
        val jwtClaimsSet = makeClaimSet(authUser, issuedAt, expiresAt)
        val params = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)
        return jwtEncoder.encode(params)
    }

    private fun makeHeader(): JwsHeader {
        val jwsAlgorithm = SignatureAlgorithm.RS256
        val jwsHeaderBuilder = JwsHeader.with(jwsAlgorithm)
        return jwsHeaderBuilder.build()
    }

    private fun makeClaimSet(authUser: AuthUser, issuedAt: AppDateTime, expiresAt: AppDateTime): JwtClaimsSet {
        return JwtClaimsSet.builder()
            .subject(authUser.userId.toString())
            .issuer(AccessToken.VALID_ISSUER)
            .issuedAt(issuedAt.toInstant())
            .expiresAt(expiresAt.toInstant())
            .claim(TOKEN_TYPE_CLAIM, AccessToken.VALID_TOKEN_TYPE)
            .claim(ROLE_CLAIM, authUser.role.name)
            .build()
    }

    private fun makeAccessTokenFromJwt(jwt: Jwt): AccessToken {
        return AccessToken.restore(
            userId = jwt.subject.toLong(),
            roleName = jwt.getClaim(ROLE_CLAIM),
            tokenValue = jwt.tokenValue,
            issuedAt = jwt.issuedAt!!,
            expiresAt = jwt.expiresAt!!
        )
    }
}
