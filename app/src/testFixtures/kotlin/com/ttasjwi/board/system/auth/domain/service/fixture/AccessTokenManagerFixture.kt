package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.exception.AccessTokenExpiredException
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.auth.domain.model.Role
import java.time.ZonedDateTime

class AccessTokenManagerFixture : AccessTokenManager {

    companion object {

        internal const val VALIDITY_MINUTES = 30L

        private const val MEMBER_ID_INDEX = 0
        private const val ROLE_INDEX = 1
        private const val ISSUED_AT_INDEX = 2
        private const val EXPIRES_AT_INDEX = 3
        private const val TOKEN_TYPE = "accessToken"
    }

    override fun generate(authMember: AuthMember, issuedAt: ZonedDateTime): AccessToken {
        val expiresAt = issuedAt.plusMinutes(VALIDITY_MINUTES)
        val tokenValue = makeTokenValue(authMember, issuedAt, expiresAt)
        return accessTokenFixture(
            memberId = authMember.memberId,
            role = authMember.role,
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
            issuedAt = ZonedDateTime.parse(split[ISSUED_AT_INDEX]),
            expiresAt = ZonedDateTime.parse(split[EXPIRES_AT_INDEX])
        )
    }

    override fun checkCurrentlyValid(accessToken: AccessToken, currentTime: ZonedDateTime) {
        if (accessToken.expiresAt <= currentTime) {
            throw AccessTokenExpiredException(accessToken.expiresAt, currentTime)
        }
    }

    private fun makeTokenValue(authMember: AuthMember, issuedAt: ZonedDateTime, expiresAt: ZonedDateTime): String {
        return "${authMember.memberId}," + // 0
                "${authMember.role.name}," + // 1
                "${issuedAt}," + // 2
                "${expiresAt}," + // 3
                TOKEN_TYPE // 4
    }

}
