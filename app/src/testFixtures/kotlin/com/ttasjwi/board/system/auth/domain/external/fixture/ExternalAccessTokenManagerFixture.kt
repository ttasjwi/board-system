package com.ttasjwi.board.system.auth.domain.external.fixture

import com.ttasjwi.board.system.auth.domain.external.ExternalAccessTokenManager
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.ZonedDateTime

class ExternalAccessTokenManagerFixture : ExternalAccessTokenManager {

    companion object {

        private const val MEMBER_ID_INDEX = 0
        private const val ROLE_INDEX = 1
        private const val ISSUED_AT_INDEX = 2
        private const val EXPIRES_AT_INDEX = 3
        private const val TOKEN_TYPE = "accessToken"
    }

    override fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken {
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
            issuedAt = AppDateTime.from(ZonedDateTime.parse(split[ISSUED_AT_INDEX])),
            expiresAt = AppDateTime.from(ZonedDateTime.parse(split[EXPIRES_AT_INDEX])),
        )
    }

    private fun makeTokenValue(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): String {
        return "${authMember.memberId}," + // 0
                "${authMember.role.name}," + // 1
                "${issuedAt}," + // 2
                "${expiresAt}," + // 3
                TOKEN_TYPE // 4
    }
}
