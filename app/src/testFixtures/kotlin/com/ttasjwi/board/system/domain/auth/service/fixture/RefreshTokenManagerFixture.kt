package com.ttasjwi.board.system.domain.auth.service.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.model.RefreshToken
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder
import com.ttasjwi.board.system.domain.auth.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.domain.auth.service.RefreshTokenManager
import java.time.ZonedDateTime
import java.util.*

class RefreshTokenManagerFixture : RefreshTokenManager {

    companion object {
        const val VALIDITY_HOURS = 24L
        private const val MEMBER_ID_INDEX = 0
        private const val REFRESH_TOKEN_ID_INDEX = 1
        private const val ISSUED_AT_INDEX = 2
        private const val EXPIRES_AT_INDEX = 3
        private const val TOKEN_TYPE = "refreshToken"
        const val REFRESH_REQUIRE_THRESHOLD_HOURS = 8L
    }

    override fun generate(memberId: Long, issuedAt: AppDateTime): RefreshToken {
        val expiresAt = issuedAt.plusHours(VALIDITY_HOURS)
        val refreshTokenId = UUID.randomUUID().toString()
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
            refreshTokenId = split[REFRESH_TOKEN_ID_INDEX],
            tokenValue = tokenValue,
            issuedAt = split[ISSUED_AT_INDEX].let { AppDateTime.from(ZonedDateTime.parse(it)) },
            expiresAt = split[EXPIRES_AT_INDEX].let { AppDateTime.from(ZonedDateTime.parse(it)) }
        )
    }

    override fun checkCurrentlyValid(
        refreshToken: RefreshToken,
        refreshTokenHolder: RefreshTokenHolder,
        currentTime: AppDateTime
    ) {
    }

    override fun isRefreshRequired(refreshToken: RefreshToken, currentTime: AppDateTime): Boolean {
        return currentTime >= refreshToken.expiresAt.minusHours(REFRESH_REQUIRE_THRESHOLD_HOURS)
    }

    private fun makeTokenValue(
        memberId: Long,
        refreshTokenId: String,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): String {
        return "${memberId}," + // 0
                "${refreshTokenId}," + // 1
                "${issuedAt}," + // 2
                "${expiresAt}," + // 3
                TOKEN_TYPE // 4
    }
}
