package com.ttasjwi.board.system.config.redis

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ttasjwi.board.system.member.domain.external.db.redis.RedisEmailVerification
import com.ttasjwi.board.system.member.domain.external.db.redis.RedisEmailVerificationSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisEmailVerificationTemplateConfig {

    @Bean
    fun redisEmailVerificationTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, RedisEmailVerification> {
        val redisTemplate = RedisTemplate<String, RedisEmailVerification>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = redisEmailVerificationSerializer()
        redisTemplate.setEnableTransactionSupport(true)

        return redisTemplate
    }

    private fun redisEmailVerificationSerializer(): RedisEmailVerificationSerializer {
        val objectMapper = jacksonObjectMapper()
        objectMapper.registerModules(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)

        return RedisEmailVerificationSerializer(objectMapper)
    }

}
