package com.ttasjwi.board.system.app.config

import com.ttasjwi.board.system.common.annotation.component.*
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
    basePackages = [
        "com.ttasjwi.board.system.article",
        "com.ttasjwi.board.system.board",
        "com.ttasjwi.board.system.member",
        "com.ttasjwi.board.system.emailsender",
    ],
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            value = [
                DomainService::class,
                DomainPolicy::class,
                AppComponent::class,
                UseCase::class,
                ApplicationProcessor::class,
                ApplicationCommandMapper::class,
                ApplicationQueryMapper::class
            ],
        )]
)
class ComponentScanConfig
