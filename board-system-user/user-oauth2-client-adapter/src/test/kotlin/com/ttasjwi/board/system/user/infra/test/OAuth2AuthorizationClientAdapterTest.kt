package com.ttasjwi.board.system.user.infra.test

import com.ttasjwi.board.system.user.infra.config.OAuth2ClientConfig
import com.ttasjwi.board.system.user.infra.oauth2.MemoryOAuth2ClientRegistrationPersistenceAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(OAuth2ClientConfig::class)
abstract class OAuth2AuthorizationClientAdapterTest {

    @Autowired
    protected lateinit var oAuth2ClientRegistrationPersistenceAdapter: MemoryOAuth2ClientRegistrationPersistenceAdapter
}
