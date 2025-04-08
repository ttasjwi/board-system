package com.ttasjwi.board.system.auth.domain.external.impl

import com.ttasjwi.board.system.auth.domain.exception.InvalidRefreshTokenFormatException
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.common.time.AppDateTime
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Component

@Component
class ExternalRefreshTokenManagerImpl(
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder
) : ExternalRefreshTokenManager {

    companion object {
        private const val REFRESH_TOKEN_ID_CLAIM = "refreshTokenId"
        private const val TOKEN_TYPE_CLAIM = "tokenType"
        private const val TOKEN_TYPE_VALUE = "refreshToken"
        private const val ISSUER_VALUE = "board-system"
    }

    override fun generate(
        memberId: Long,
        refreshTokenId: String,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): RefreshToken {
        val jwt = makeJwt(memberId, refreshTokenId, issuedAt, expiresAt)
        return makeRefreshTokenFromJwt(jwt)
    }

    override fun parse(tokenValue: String): RefreshToken {
        val jwt: Jwt
        try {
            jwt = jwtDecoder.decode(tokenValue)
        } catch (e: JwtException) {
            throw InvalidRefreshTokenFormatException(e)
        }

        val tokenType = jwt.getClaim<String>(TOKEN_TYPE_CLAIM)

        if (tokenType != TOKEN_TYPE_VALUE) {
            throw InvalidRefreshTokenFormatException()
        }
        return makeRefreshTokenFromJwt(jwt)
    }

    private fun makeJwt(
        memberId: Long,
        refreshTokenId: String,
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
        refreshTokenId: String,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): JwtClaimsSet {
        return JwtClaimsSet.builder()
            .subject(memberId.toString())
            .issuer(ISSUER_VALUE)
            .issuedAt(issuedAt.toInstant())
            .expiresAt(expiresAt.toInstant())
            .claim(REFRESH_TOKEN_ID_CLAIM, refreshTokenId)
            .claim(TOKEN_TYPE_CLAIM, TOKEN_TYPE_VALUE)
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
