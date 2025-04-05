package com.ttasjwi.board.system.global.config

import com.ttasjwi.board.system.global.time.TimeManager
import com.ttasjwi.board.system.global.time.impl.TimeManagerImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TimeConfig {

    @Bean
    fun timeManager(): TimeManager {
        return TimeManagerImpl()
    }
}
