package com.ttasjwi.board.system.auth.domain.external.fixture

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

class ExternalRefreshTokenManagerFixture : ExternalRefreshTokenManager {

    companion object {
        private const val MEMBER_ID_INDEX = 0
        private const val REFRESH_TOKEN_ID_INDEX = 1
        private const val ISSUED_AT_INDEX = 2
        private const val EXPIRES_AT_INDEX = 3
        private const val TOKEN_TYPE = "refreshToken"
    }

    override fun generate(
        memberId: MemberId,
        refreshTokenId: RefreshTokenId,
        issuedAt: ZonedDateTime,
        expiresAt: ZonedDateTime
    ): RefreshToken {
        val tokenValue = makeTokenValue(memberId, refreshTokenId.value, issuedAt, expiresAt)

        return refreshTokenFixture(
            memberId = memberId.value,
            refreshTokenId = refreshTokenId.value,
            tokenValue = tokenValue,
            issuedAt = issuedAt,
            expiresAt = expiresAt
        )
    }

    override fun parse(tokenValue: String): RefreshToken {
        val split = tokenValue.split(",")

        return refreshTokenFixture(
            memberId = split[MEMBER_ID_INDEX].toLong(),
            refreshTokenId = split[REFRESH_TOKEN_ID_INDEX],
            tokenValue = tokenValue,
            issuedAt = split[ISSUED_AT_INDEX].let { ZonedDateTime.parse(it) },
            expiresAt = split[EXPIRES_AT_INDEX].let { ZonedDateTime.parse(it) }
        )
    }

    private fun makeTokenValue(
        memberId: MemberId,
        refreshTokenId: String,
        issuedAt: ZonedDateTime,
        expiresAt: ZonedDateTime
    ): String {
        return "${memberId.value}," + // 0
                "${refreshTokenId}," + // 1
                "${issuedAt}," + // 2
                "${expiresAt}," + // 3
                TOKEN_TYPE // 4
    }
}
