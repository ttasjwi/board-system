package com.ttasjwi.board.system.user.infra

import com.ttasjwi.board.system.user.infra.oauth2.MemoryOAuth2ClientRegistrationPersistenceAdapter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OAuth2AuthorizationClientAdapterTestApplication

fun main(args: Array<String>) {
    runApplication<MemoryOAuth2ClientRegistrationPersistenceAdapter>(*args)
}
