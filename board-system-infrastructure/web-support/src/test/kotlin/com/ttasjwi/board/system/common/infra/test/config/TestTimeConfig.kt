package com.ttasjwi.board.system.common.infra.test.config

import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestTimeConfig {

    @Bean
    fun timeManagerFixture(): TimeManagerFixture {
        return TimeManagerFixture()
    }
}
