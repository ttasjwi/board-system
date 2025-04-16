package com.ttasjwi.board.system.user.infra.test

import com.ttasjwi.board.system.user.infra.persistence.CustomOAuth2AuthorizationRequestRepository
import com.ttasjwi.board.system.user.infra.persistence.EmailVerificationPersistenceAdapter
import com.ttasjwi.board.system.user.infra.persistence.UserRefreshTokenIdListPersistenceAdapter
import com.ttasjwi.board.system.user.infra.persistence.RefreshTokenIdPersistenceAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate

@SpringBootTest
abstract class UserRedisAdapterTest {

    @Autowired
    protected lateinit var emailVerificationPersistenceAdapter: EmailVerificationPersistenceAdapter

    @Autowired
    protected lateinit var userRefreshTokenIdListPersistenceAdapter: UserRefreshTokenIdListPersistenceAdapter

    @Autowired
    protected lateinit var refreshTokenIdPersistenceAdapter: RefreshTokenIdPersistenceAdapter

    @Autowired
    protected lateinit var customOAuth2AuthorizationRequestRepository: CustomOAuth2AuthorizationRequestRepository

    @Autowired
    protected lateinit var redisTemplate: StringRedisTemplate
}
