package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationAppender
import com.ttasjwi.board.system.member.domain.service.EmailVerificationFinder
import com.ttasjwi.board.system.member.domain.service.impl.redis.RedisEmailVerification
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class EmailVerificationStorage(
    private val redisTemplate: RedisTemplate<String, RedisEmailVerification>
) : EmailVerificationAppender, EmailVerificationFinder {

    companion object {
        private const val PREFIX = "Board-System:EmailVerification:"
    }

    override fun append(emailVerification: EmailVerification, expiresAt: ZonedDateTime) {
        val key = makeKey(emailVerification.email)
        val redisModel = RedisEmailVerification.from(emailVerification)

        redisTemplate.opsForValue().set(key, redisModel)
        redisTemplate.expireAt(key, expiresAt.toInstant())
    }

    override fun removeByEmail(email: Email) {
        val key = makeKey(email)
        redisTemplate.delete(key)
    }

    override fun findByEmailOrNull(email: Email): EmailVerification? {
        val key = makeKey(email)
        return redisTemplate.opsForValue().get(key)?.restoreDomain()
    }

    private fun makeKey(email: Email): String {
        return PREFIX + email.value
    }
}
