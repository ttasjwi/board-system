package com.ttasjwi.board.system.common.eventpublisher.config

import com.ttasjwi.board.system.common.eventpublisher.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventPublisherConfig {

    @Bean
    fun eventPublisher(applicationEventPublisher: ApplicationEventPublisher): EventPublisher {
        return EventPublisher(applicationEventPublisher)
    }
}
