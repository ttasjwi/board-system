package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderStorageImpl
import com.ttasjwi.board.system.external.spring.security.oauth2.RedisOAuth2AuthorizationRequestRepository
import com.ttasjwi.board.system.member.domain.service.impl.EmailVerificationStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
abstract class RedisTest {

    @Autowired
    protected lateinit var emailVerificationStorage: EmailVerificationStorage

    @Autowired
    protected lateinit var externalRefreshTokenHolderStorage: ExternalRefreshTokenHolderStorageImpl

    @Autowired
    protected lateinit var redisOAuth2AuthorizationRequestRepository: RedisOAuth2AuthorizationRequestRepository
}
