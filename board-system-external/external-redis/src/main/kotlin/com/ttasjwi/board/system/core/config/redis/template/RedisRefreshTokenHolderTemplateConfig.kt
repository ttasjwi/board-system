package com.ttasjwi.board.system.core.config.redis.template

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ttasjwi.board.system.auth.domain.external.redis.RedisRefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.external.redis.RedisRefreshTokenHolderSerializer
import com.ttasjwi.board.system.member.domain.service.impl.redis.RedisEmailVerification
import com.ttasjwi.board.system.member.domain.service.impl.redis.RedisEmailVerificationSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisRefreshTokenHolderTemplateConfig {

    @Bean
    fun redisRefreshTokenHolderTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, RedisRefreshTokenHolder> {
        val redisTemplate = RedisTemplate<String, RedisRefreshTokenHolder>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = redisRefreshTokenHolderSerializer()
        redisTemplate.setEnableTransactionSupport(true)

        return redisTemplate
    }

    private fun redisRefreshTokenHolderSerializer(): RedisRefreshTokenHolderSerializer {
        val objectMapper = jacksonObjectMapper()
        objectMapper.registerModules(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)

        return RedisRefreshTokenHolderSerializer(objectMapper)
    }
}
