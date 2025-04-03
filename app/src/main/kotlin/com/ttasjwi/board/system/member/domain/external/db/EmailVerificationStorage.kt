package com.ttasjwi.board.system.member.domain.external.db

import com.ttasjwi.board.system.common.dataserializer.DataSerializer
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.external.db.redis.RedisEmailVerification
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationAppender
import com.ttasjwi.board.system.member.domain.service.EmailVerificationFinder
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class EmailVerificationStorage(
    private val redisTemplate: StringRedisTemplate
) : EmailVerificationAppender, EmailVerificationFinder {

    companion object {

        // Board-System:EmailVerification:{email}
        private const val KEY_PREFIX = "Board-System:EmailVerification:"
    }

    override fun append(emailVerification: EmailVerification, expiresAt: AppDateTime) {
        val key = makeKey(emailVerification.email)
        val redisModel = RedisEmailVerification.from(emailVerification)
        redisTemplate.opsForValue().set(key, DataSerializer.serialize(redisModel))
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun removeByEmail(email: String) {
        val key = makeKey(email)
        redisTemplate.delete(key)
    }

    override fun findByEmailOrNull(email: String): EmailVerification? {
        val key = makeKey(email)
        return redisTemplate.opsForValue().get(key)
            ?.let {
                DataSerializer.deserialize(it, RedisEmailVerification::class.java).restoreDomain()
            }
    }

    private fun makeKey(email: String): String {
        return KEY_PREFIX + email
    }
}
