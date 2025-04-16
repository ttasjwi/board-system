package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.dataserializer.DataSerializer
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.EmailVerification
import com.ttasjwi.board.system.user.domain.port.EmailVerificationPersistencePort
import com.ttasjwi.board.system.user.infra.persistence.redis.RedisEmailVerification
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class EmailVerificationPersistenceAdapter(
    private val redisTemplate: StringRedisTemplate
) : EmailVerificationPersistencePort {

    companion object {

        // Board-System:EmailVerification:{email}
        private const val KEY_PREFIX = "Board-System:EmailVerification:"
    }

    override fun save(emailVerification: EmailVerification, expiresAt: AppDateTime) {
        val key = generateKey(emailVerification.email)
        val redisModel = RedisEmailVerification.from(emailVerification)
        redisTemplate.opsForValue().set(key, DataSerializer.serialize(redisModel))
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun remove(email: String) {
        val key = generateKey(email)
        redisTemplate.delete(key)
    }

    override fun findByEmailOrNull(email: String): EmailVerification? {
        val key = generateKey(email)
        return redisTemplate.opsForValue().get(key)
            ?.let {
                DataSerializer.deserialize(it, RedisEmailVerification::class.java).restoreDomain()
            }
    }

    private fun generateKey(email: String): String {
        return KEY_PREFIX + email
    }
}
