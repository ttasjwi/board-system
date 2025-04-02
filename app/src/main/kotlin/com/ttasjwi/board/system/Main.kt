package com.ttasjwi.board.system

import com.ttasjwi.board.system.common.annotation.component.DomainPolicy
import com.ttasjwi.board.system.common.annotation.component.DomainService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@ConfigurationPropertiesScan
@ComponentScan(
    includeFilters = [ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = [
            DomainService::class,
            DomainPolicy::class,
        ]
    )]
)
@SpringBootApplication
class Main

fun main(args: Array<String>) {
    runApplication<Main>(*args)
}
