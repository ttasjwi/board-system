package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime
import java.util.*

class RefreshTokenManagerFixture : RefreshTokenManager {

    companion object {
        internal const val VALIDITY_HOURS = 24L
        private const val MEMBER_ID_INDEX = 0
        private const val REFRESH_TOKEN_ID_INDEX = 1
        private const val ISSUED_AT_INDEX = 2
        private const val EXPIRES_AT_INDEX = 3
        private const val TOKEN_TYPE = "refreshToken"
        const val REFRESH_REQUIRE_THRESHOLD_HOURS = 8L
    }

    override fun generate(memberId: MemberId, issuedAt: ZonedDateTime): RefreshToken {
        val expiresAt = issuedAt.plusHours(VALIDITY_HOURS)
        val refreshTokenId = UUID.randomUUID().toString()
        val tokenValue = makeTokenValue(memberId, refreshTokenId, issuedAt, expiresAt)

        return refreshTokenFixture(
            memberId = memberId.value,
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
            issuedAt = split[ISSUED_AT_INDEX].let { ZonedDateTime.parse(it) },
            expiresAt = split[EXPIRES_AT_INDEX].let { ZonedDateTime.parse(it) }
        )
    }

    override fun checkCurrentlyValid(
        refreshToken: RefreshToken,
        refreshTokenHolder: RefreshTokenHolder,
        currentTime: ZonedDateTime
    ) {}

    override fun isRefreshRequired(refreshToken: RefreshToken, currentTime: ZonedDateTime): Boolean {
        return currentTime >= refreshToken.expiresAt.minusHours(REFRESH_REQUIRE_THRESHOLD_HOURS)
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
