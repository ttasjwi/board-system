package com.ttasjwi.board.system.common.token.fixture

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.token.AccessToken
import com.ttasjwi.board.system.common.token.AccessTokenParser
import java.time.ZonedDateTime

class AccessTokenParserFixture : AccessTokenParser {

    companion object {
        private const val MEMBER_ID_INDEX = 0
        private const val ROLE_INDEX = 1
        private const val TOKEN_TYPE_INDEX = 2
        private const val ISSUER_INDEX = 3
        private const val ISSUED_AT_INDEX = 4
        private const val EXPIRES_AT_INDEX = 5
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

    private fun makeTokenValue(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): String {
        return "${authMember.memberId}," + // 0
                "${authMember.role.name}," + // 1
                "${AccessToken.VALID_TOKEN_TYPE}," + // 2
                "${AccessToken.VALID_ISSUER}," + // 3
                "${issuedAt}," + // 4
                "$expiresAt" // 5
    }

    fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken {
        val tokenValue = makeTokenValue(authMember, issuedAt, expiresAt)
        return accessTokenFixture(
            memberId = authMember.memberId,
            role = authMember.role,
            tokenValue = tokenValue,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        )
    }
}
