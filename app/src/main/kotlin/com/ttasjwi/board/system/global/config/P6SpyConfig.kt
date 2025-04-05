package com.ttasjwi.board.system.global.config

import com.ttasjwi.board.system.global.persistence.P6SpyEventListener
import com.ttasjwi.board.system.global.persistence.P6SpyFormattingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class P6SpyConfig {

    @Bean
    fun p6SpyEventListener(): P6SpyEventListener {
        return P6SpyEventListener()
    }

    @Bean
    fun p6SpyFormattingStrategy(): P6SpyFormattingStrategy {
        return P6SpyFormattingStrategy()
    }
}
