package com.ttasjwi.board.system.emailsender

import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class EmailSenderApplicationTestConfig {

    @Bean
    fun messageResolver(): MessageResolver {
        return MessageResolverFixture()
    }
}
