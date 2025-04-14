package com.ttasjwi.board.system.common.auth.infra.token

import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.auth.RefreshTokenGeneratePort
import com.ttasjwi.board.system.common.time.AppDateTime
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.*

class JwtRefreshTokenGenerateAdapter(
    private val jwtEncoder: JwtEncoder,
) : RefreshTokenGeneratePort {

    companion object {
        private const val REFRESH_TOKEN_ID_CLAIM = "refreshTokenId"
        private const val TOKEN_TYPE_CLAIM = "tokenType"
    }

    override fun generate(
        memberId: Long,
        refreshTokenId: Long,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): RefreshToken {
        val jwt = makeJwt(memberId, refreshTokenId, issuedAt, expiresAt)
        return makeRefreshTokenFromJwt(jwt)
    }

    private fun makeJwt(
        memberId: Long,
        refreshTokenId: Long,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): Jwt {
        val jwsHeader = makeHeader()
        val jwtClaimsSet = makeClaimSet(memberId, refreshTokenId, issuedAt, expiresAt)
        val params = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)

        return jwtEncoder.encode(params)
    }

    private fun makeHeader(): JwsHeader {
        val jwsAlgorithm = SignatureAlgorithm.RS256
        val jwsHeaderBuilder = JwsHeader.with(jwsAlgorithm)
        return jwsHeaderBuilder.build()
    }

    private fun makeClaimSet(
        memberId: Long,
        refreshTokenId: Long,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): JwtClaimsSet {
        return JwtClaimsSet.builder()
            .subject(memberId.toString())
            .issuer(RefreshToken.VALID_ISSUER)
            .issuedAt(issuedAt.toInstant())
            .expiresAt(expiresAt.toInstant())
            .claim(REFRESH_TOKEN_ID_CLAIM, refreshTokenId)
            .claim(TOKEN_TYPE_CLAIM, RefreshToken.VALID_TOKEN_TYPE)
            .build()
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
