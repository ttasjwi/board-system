package com.ttasjwi.board.system.core.config.redis.template

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ttasjwi.board.system.external.spring.security.oauth2.redis.RedisOAuth2AuthorizationRequest
import com.ttasjwi.board.system.external.spring.security.oauth2.redis.RedisOAuth2AuthorizationRequestSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisOAuth2AuthorizationTemplateConfig {

    @Bean
    fun redisOAuth2AuthorizationTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, RedisOAuth2AuthorizationRequest> {
        val redisTemplate = RedisTemplate<String, RedisOAuth2AuthorizationRequest>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = redisOAuth2AuthorizationRequestSerializer()
        redisTemplate.setEnableTransactionSupport(true)

        return redisTemplate
    }

    private fun redisOAuth2AuthorizationRequestSerializer(): RedisOAuth2AuthorizationRequestSerializer {
        val objectMapper = jacksonObjectMapper()
        objectMapper.registerModules(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)

        return RedisOAuth2AuthorizationRequestSerializer(objectMapper)
    }
}
