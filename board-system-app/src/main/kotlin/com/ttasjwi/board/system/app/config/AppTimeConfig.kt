package com.ttasjwi.board.system.app.config

import com.ttasjwi.board.system.common.time.TimeManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppTimeConfig {

    @Bean
    fun timeManager(): TimeManager {
        return TimeManager.default()
    }
}
