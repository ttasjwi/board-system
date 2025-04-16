package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.*
import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.ZonedDateTime

class AccessTokenPortFixture : AccessTokenGeneratePort, AccessTokenParsePort {

    companion object {
        private const val MEMBER_ID_INDEX = 0
        private const val ROLE_INDEX = 1
        private const val TOKEN_TYPE_INDEX = 2
        private const val ISSUER_INDEX = 3
        private const val ISSUED_AT_INDEX = 4
        private const val EXPIRES_AT_INDEX = 5
    }

    override fun generate(authUser: AuthUser, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken {
        val tokenValue = makeTokenValue(authUser, issuedAt, expiresAt)
        return accessTokenFixture(
            memberId = authUser.userId,
            role = authUser.role,
            tokenValue = tokenValue,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        )
    }

    override fun parse(tokenValue: String): AccessToken {
        val split = tokenValue.split(",")
        return accessTokenFixture(
            memberId = split[MEMBER_ID_INDEX].toLong(),
            role = split[ROLE_INDEX].let { Role.restore(it) },
            tokenValue = tokenValue,
            issuedAt = AppDateTime.from(ZonedDateTime.parse(split[ISSUED_AT_INDEX])),
            expiresAt = AppDateTime.from(ZonedDateTime.parse(split[EXPIRES_AT_INDEX])),
        )
    }

    private fun makeTokenValue(authUser: AuthUser, issuedAt: AppDateTime, expiresAt: AppDateTime): String {
        return "${authUser.userId}," + // 0
                "${authUser.role.name}," + // 1
                "${AccessToken.VALID_TOKEN_TYPE}," + // 2
                "${AccessToken.VALID_ISSUER}," + // 3
                "${issuedAt}," + // 4
                "$expiresAt" // 5
    }
}
