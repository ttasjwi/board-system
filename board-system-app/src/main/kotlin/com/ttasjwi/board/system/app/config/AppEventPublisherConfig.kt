package com.ttasjwi.board.system.app.config

import com.ttasjwi.board.system.common.infra.eventpublisher.config.EventPublisherConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(EventPublisherConfig::class)
class AppEventPublisherConfig
