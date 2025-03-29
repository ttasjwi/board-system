package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.domain.external.ExternalAccessTokenManager
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.external.impl.ExternalRefreshTokenHolderStorageImpl
import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.board.domain.external.db.BoardStorageImpl
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.member.domain.external.db.EmailVerificationStorage
import com.ttasjwi.board.system.member.domain.external.db.MemberStorageImpl
import com.ttasjwi.board.system.member.domain.external.impl.ExternalPasswordHandlerImpl
import com.ttasjwi.board.system.member.domain.service.EmailVerificationStartedEventPublisher
import com.ttasjwi.board.system.member.domain.service.SocialConnectionStorage
import com.ttasjwi.board.system.spring.security.oauth2.redis.RedisOAuth2AuthorizationRequestRepository
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
abstract class IntegrationTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var timeManagerFixture: TimeManagerFixture

    @Autowired
    protected lateinit var timeManager: TimeManager

    @Autowired
    protected lateinit var memberStorageImpl: MemberStorageImpl

    @Autowired
    protected lateinit var socialConnectionStorage: SocialConnectionStorage

    @Autowired
    protected lateinit var boardStorageImpl: BoardStorageImpl

    @Autowired
    protected lateinit var emailVerificationStorage: EmailVerificationStorage

    @Autowired
    protected lateinit var externalPasswordHandler: ExternalPasswordHandlerImpl

    @Autowired
    protected lateinit var externalRefreshTokenManager: ExternalRefreshTokenManager

    @Autowired
    protected lateinit var accessTokenManager: AccessTokenManager

    @Autowired
    protected lateinit var refreshTokenManager: RefreshTokenManager

    @Autowired
    protected lateinit var externalAccessTokenManager: ExternalAccessTokenManager

    @Autowired
    protected lateinit var externalRefreshTokenHolderStorage: ExternalRefreshTokenHolderStorageImpl

    @Autowired
    protected lateinit var redisOAuth2AuthorizationRequestRepository: RedisOAuth2AuthorizationRequestRepository

    @Autowired
    protected lateinit var emailVerificationStartedEventPublisher: EmailVerificationStartedEventPublisher

    @Autowired
    private lateinit var em: EntityManager

    protected fun flushAndClearEntityManager() {
        em.flush()
        em.clear()
    }

}
