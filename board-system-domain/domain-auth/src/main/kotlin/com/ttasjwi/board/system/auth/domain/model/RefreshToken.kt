package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.auth.domain.exception.RefreshTokenExpiredException
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

class RefreshToken
internal constructor(
    val memberId: MemberId,
    val refreshTokenId: RefreshTokenId,
    val tokenValue: String,
    val issuedAt: ZonedDateTime,
    val expiresAt: ZonedDateTime,
) {

    companion object {

        internal const val VALIDITY_HOURS = 24L


        private val log = getLogger(RefreshToken::class.java)

        fun restore(
            memberId: Long,
            refreshTokenId: String,
            tokenValue: String,
            issuedAt: ZonedDateTime,
            expiresAt: ZonedDateTime
        ): RefreshToken {
            return RefreshToken(
                memberId = MemberId.restore(memberId),
                refreshTokenId = RefreshTokenId.restore(refreshTokenId),
                tokenValue = tokenValue,
                issuedAt = issuedAt,
                expiresAt = expiresAt
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RefreshToken) return false
        if (memberId != other.memberId) return false
        if (refreshTokenId != other.refreshTokenId) return false
        if (tokenValue != other.tokenValue) return false
        if (issuedAt != other.issuedAt) return false
        if (expiresAt != other.expiresAt) return false
        return true
    }

    override fun hashCode(): Int {
        var result = memberId.hashCode()
        result = 31 * result + refreshTokenId.hashCode()
        result = 31 * result + tokenValue.hashCode()
        result = 31 * result + issuedAt.hashCode()
        result = 31 * result + expiresAt.hashCode()
        return result
    }

    /**
     * 리프레시토큰이 현재 유효한 지 검증
     */
    internal fun checkCurrentlyValid(currentTime: ZonedDateTime) {
        if (currentTime >= this.expiresAt) {
            val ex = RefreshTokenExpiredException(
                debugMessage = "리프레시토큰 유효시간이 경과되어 만료됨(currentTime=${currentTime},expiresAt=${this.expiresAt})"
            )
            log.warn(ex)
            throw ex
        }
    }
}
