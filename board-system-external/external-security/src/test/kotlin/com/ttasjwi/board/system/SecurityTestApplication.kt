package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.domain.service.fixture.AccessTokenManagerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@ConfigurationPropertiesScan
@SpringBootApplication
class SecurityTestApplication {

    @Bean
    fun accessTokenManagerFixture(): AccessTokenManagerFixture {
        return AccessTokenManagerFixture()
    }

    @Bean
    fun timeManagerFixture(): TimeManagerFixture {
        return TimeManagerFixture()
    }
}

fun main(args: Array<String>) {
    runApplication<SecurityTestApplication>(*args)
}
