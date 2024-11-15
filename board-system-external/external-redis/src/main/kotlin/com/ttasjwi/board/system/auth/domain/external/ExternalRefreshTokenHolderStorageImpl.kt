package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.external.redis.RedisRefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.member.domain.model.MemberId
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class ExternalRefreshTokenHolderStorageImpl(
    private val redisTemplate: RedisTemplate<String, RedisRefreshTokenHolder>,
) : ExternalRefreshTokenHolderAppender, ExternalRefreshTokenHolderFinder {

    companion object {
        private const val PREFIX = "Board-System:RefreshTokenHolder:"
    }

    override fun append(memberId: MemberId, refreshTokenHolder: RefreshTokenHolder, expiresAt: ZonedDateTime) {
        val key = makeKey(memberId)
        val redisModel = RedisRefreshTokenHolder.from(refreshTokenHolder)

        redisTemplate.opsForValue().set(key, redisModel)
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun findByMemberIdOrNull(memberId: MemberId): RefreshTokenHolder? {
        val key = makeKey(memberId)
        return redisTemplate.opsForValue().get(key)?.restoreDomain()
    }

    override fun removeByMemberId(memberId: MemberId) {
        val key = makeKey(memberId)
        redisTemplate.delete(key)
    }

    private fun makeKey(memberId: MemberId): String {
        return PREFIX + memberId.value
    }
}
