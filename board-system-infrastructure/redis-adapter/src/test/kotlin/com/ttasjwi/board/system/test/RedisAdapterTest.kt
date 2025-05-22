package com.ttasjwi.board.system.test

import com.ttasjwi.board.system.user.infra.persistence.EmailVerificationPersistenceAdapter
import com.ttasjwi.board.system.user.infra.persistence.OAuth2AuthorizationRequestPersistenceAdapter
import com.ttasjwi.board.system.user.infra.persistence.RefreshTokenIdPersistenceAdapter
import com.ttasjwi.board.system.user.infra.persistence.UserRefreshTokenIdListPersistenceAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate

@SpringBootTest
abstract class RedisAdapterTest {

    @Autowired
    protected lateinit var emailVerificationPersistenceAdapter: EmailVerificationPersistenceAdapter

    @Autowired
    protected lateinit var userRefreshTokenIdListPersistenceAdapter: UserRefreshTokenIdListPersistenceAdapter

    @Autowired
    protected lateinit var refreshTokenIdPersistenceAdapter: RefreshTokenIdPersistenceAdapter

    @Autowired
    protected lateinit var oAuth2AuthorizationRequestPersistenceAdapter: OAuth2AuthorizationRequestPersistenceAdapter

    @Autowired
    protected lateinit var redisTemplate: StringRedisTemplate
}
