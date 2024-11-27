package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.application.usecase.fixture.SocialLoginUseCaseFixture
import com.ttasjwi.board.system.auth.domain.service.fixture.AccessTokenManagerFixture
import com.ttasjwi.board.system.core.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.core.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@ConfigurationPropertiesScan
@SpringBootApplication
class SecurityTestApplication {

    @Bean
    fun messageResolverFixture(): MessageResolverFixture {
        return MessageResolverFixture()
    }

    @Bean
    fun localeManagerFixture(): LocaleManagerFixture {
        return LocaleManagerFixture()
    }

    @Bean
    fun accessTokenManagerFixture(): AccessTokenManagerFixture {
        return AccessTokenManagerFixture()
    }

    @Bean
    fun timeManagerFixture(): TimeManagerFixture {
        return TimeManagerFixture()
    }

    @Bean
    fun socialLoginUseCaseFixture(): SocialLoginUseCaseFixture {
        return SocialLoginUseCaseFixture()
    }
}

fun main(args: Array<String>) {
    runApplication<SecurityTestApplication>(*args)
}
