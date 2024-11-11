package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.member.domain.model.Role
import java.time.ZonedDateTime

class AccessTokenManagerFixture : AccessTokenManager {

    companion object {

        internal const val VALIDITY_MINUTES = 30L

        private const val MEMBER_ID_INDEX = 0
        private const val EMAIL_INDEX = 1
        private const val USERNAME_INDEX = 2
        private const val NICKNAME_INDEX = 3
        private const val ROLE_INDEX = 4
        private const val ISSUED_AT_INDEX = 5
        private const val EXPIRES_AT_INDEX = 6
        private const val TOKEN_TYPE = "accessToken"
    }

    override fun generate(authMember: AuthMember, issuedAt: ZonedDateTime): AccessToken {
        val expiresAt = issuedAt.plusMinutes(VALIDITY_MINUTES)
        val tokenValue = makeTokenValue(authMember, issuedAt, expiresAt)
        return accessTokenFixture(
            memberId = authMember.memberId.value,
            email = authMember.email.value,
            username = authMember.username.value,
            nickname = authMember.nickname.value,
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
            email = split[EMAIL_INDEX],
            username = split[USERNAME_INDEX],
            nickname = split[NICKNAME_INDEX],
            role = split[ROLE_INDEX].let { Role.restore(it) },
            tokenValue = tokenValue,
            issuedAt = ZonedDateTime.parse(split[ISSUED_AT_INDEX]),
            expiresAt = ZonedDateTime.parse(split[EXPIRES_AT_INDEX])
        )
    }

    private fun makeTokenValue(authMember: AuthMember, issuedAt: ZonedDateTime, expiresAt: ZonedDateTime): String {
        return "${authMember.memberId.value}," + // 0
                "${authMember.email.value}," + // 1
                "${authMember.username.value}," + // 2
                "${authMember.nickname.value}," + // 3
                "${authMember.role.name}," + // 4
                "${issuedAt}," + // 5
                "${expiresAt}," + // 6
                TOKEN_TYPE // 7
    }

}
