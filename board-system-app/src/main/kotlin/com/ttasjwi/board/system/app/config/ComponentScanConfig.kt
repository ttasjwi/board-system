package com.ttasjwi.board.system.app.config

import com.ttasjwi.board.system.common.annotation.component.DomainService
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
    basePackages = [
        "com.ttasjwi.board.system.auth",
        "com.ttasjwi.board.system.board",
        "com.ttasjwi.board.system.member",
    ],
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            value = [
                DomainService::class,
            ],
        )]
)
class ComponentScanConfig
