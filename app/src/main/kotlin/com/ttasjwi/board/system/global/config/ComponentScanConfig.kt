package com.ttasjwi.board.system.global.config

import com.ttasjwi.board.system.global.annotation.*
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
    basePackages = ["com.ttasjwi.board.system"],
    includeFilters = [ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        value = [
            DomainService::class,
            ApplicationService::class,
            ApplicationProcessor::class,
            ApplicationCommandMapper::class,
            ApplicationQueryMapper::class
        ]
    )]
)
class ComponentScanConfig
