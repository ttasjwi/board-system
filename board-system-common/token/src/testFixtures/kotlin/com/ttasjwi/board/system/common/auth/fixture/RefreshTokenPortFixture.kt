package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.auth.RefreshTokenGeneratePort
import com.ttasjwi.board.system.common.auth.RefreshTokenParsePort
import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.ZonedDateTime

class RefreshTokenPortFixture : RefreshTokenGeneratePort, RefreshTokenParsePort {

    companion object {
        private const val MEMBER_ID_INDEX = 0
        private const val REFRESH_TOKEN_ID_INDEX = 1
        private const val TOKEN_TYPE_INDEX = 2
        private const val ISSUER_INDEX = 3
        private const val ISSUED_AT_INDEX = 4
        private const val EXPIRES_AT_INDEX = 5
    }

    override fun generate(
        memberId: Long,
        refreshTokenId: Long,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): RefreshToken {
        val tokenValue = makeTokenValue(memberId, refreshTokenId, issuedAt, expiresAt)

        return refreshTokenFixture(
            memberId = memberId,
            refreshTokenId = refreshTokenId,
            tokenValue = tokenValue,
            issuedAt = issuedAt,
            expiresAt = expiresAt
        )
    }

    override fun parse(tokenValue: String): RefreshToken {
        val split = tokenValue.split(",")

        return refreshTokenFixture(
            memberId = split[MEMBER_ID_INDEX].toLong(),
            refreshTokenId = split[REFRESH_TOKEN_ID_INDEX].toLong(),
            tokenValue = tokenValue,
            issuedAt = split[ISSUED_AT_INDEX].let { AppDateTime.from(ZonedDateTime.parse(it)) },
            expiresAt = split[EXPIRES_AT_INDEX].let { AppDateTime.from(ZonedDateTime.parse(it)) }
        )
    }

    private fun makeTokenValue(
        memberId: Long,
        refreshTokenId: Long,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): String {
        return "${memberId}," + // 0
                "${refreshTokenId}," + // 1
                "${RefreshToken.VALID_TOKEN_TYPE}," + // 2
                "${RefreshToken.VALID_ISSUER}," + // 3
                "${issuedAt}," + // 4
                "$expiresAt" // 5
    }
}
