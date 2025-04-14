package com.ttasjwi.board.system.common.auth.infra.token

import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.AccessTokenGeneratePort
import com.ttasjwi.board.system.common.auth.AuthMember
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

    override fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken {
        val jwt = makeJwt(authMember, issuedAt, expiresAt)
        return makeAccessTokenFromJwt(jwt)
    }

    private fun makeJwt(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): Jwt {
        val jwsHeader = makeHeader()
        val jwtClaimsSet = makeClaimSet(authMember, issuedAt, expiresAt)
        val params = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)
        return jwtEncoder.encode(params)
    }

    private fun makeHeader(): JwsHeader {
        val jwsAlgorithm = SignatureAlgorithm.RS256
        val jwsHeaderBuilder = JwsHeader.with(jwsAlgorithm)
        return jwsHeaderBuilder.build()
    }

    private fun makeClaimSet(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): JwtClaimsSet {
        return JwtClaimsSet.builder()
            .subject(authMember.memberId.toString())
            .issuer(AccessToken.VALID_ISSUER)
            .issuedAt(issuedAt.toInstant())
            .expiresAt(expiresAt.toInstant())
            .claim(TOKEN_TYPE_CLAIM, AccessToken.VALID_TOKEN_TYPE)
            .claim(ROLE_CLAIM, authMember.role.name)
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
