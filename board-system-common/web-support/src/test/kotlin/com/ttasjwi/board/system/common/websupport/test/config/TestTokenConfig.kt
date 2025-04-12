package com.ttasjwi.board.system.common.websupport.test.config

import com.ttasjwi.board.system.common.auth.fixture.AccessTokenPortFixture
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TestTokenConfig {

    @Bean
    fun accessTokenPort(): AccessTokenPortFixture {
        return AccessTokenPortFixture()
    }
}
