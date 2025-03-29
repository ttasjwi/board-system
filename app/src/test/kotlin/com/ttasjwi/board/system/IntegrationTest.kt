package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.domain.external.ExternalAccessTokenManager
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.external.impl.ExternalRefreshTokenHolderStorageImpl
import com.ttasjwi.board.system.auth.domain.service.fixture.AccessTokenManagerFixture
import com.ttasjwi.board.system.board.domain.external.db.BoardStorageImpl
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.member.domain.external.db.EmailVerificationStorage
import com.ttasjwi.board.system.member.domain.external.db.MemberStorageImpl
import com.ttasjwi.board.system.member.domain.external.impl.ExternalPasswordHandlerImpl
import com.ttasjwi.board.system.member.domain.service.SocialConnectionStorage
import com.ttasjwi.board.system.spring.security.oauth2.redis.RedisOAuth2AuthorizationRequestRepository
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
abstract class IntegrationTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

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

    @MockBean
    private lateinit var clientRegistrationRepository: ClientRegistrationRepository

    @MockBean
    private lateinit var authorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest>

    @Autowired
    protected lateinit var accessTokenManagerFixture: AccessTokenManagerFixture

    @Autowired
    protected lateinit var timeManagerFixture: TimeManagerFixture

    @Autowired
    protected lateinit var externalRefreshTokenManager: ExternalRefreshTokenManager

    @Autowired
    protected lateinit var externalAccessTokenManager: ExternalAccessTokenManager

    @Autowired
    protected lateinit var externalRefreshTokenHolderStorage: ExternalRefreshTokenHolderStorageImpl

    @Autowired
    protected lateinit var redisOAuth2AuthorizationRequestRepository: RedisOAuth2AuthorizationRequestRepository

    @Autowired
    private lateinit var em: EntityManager

    protected fun flushAndClearEntityManager() {
        em.flush()
        em.clear()
    }
}
