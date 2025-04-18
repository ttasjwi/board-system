package com.ttasjwi.board.system.test.config

import com.ttasjwi.board.system.common.auth.fixture.AccessTokenPortFixture
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestTokenConfig {

    @Bean
    fun accessTokenPortFixture(): AccessTokenPortFixture {
        return AccessTokenPortFixture()
    }
}
