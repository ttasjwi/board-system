package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.domain.external.ExternalAccessTokenManager
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.external.impl.ExternalRefreshTokenHolderStorageImpl
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.board.domain.external.db.BoardStorageImpl
import com.ttasjwi.board.system.domain.member.external.db.EmailVerificationStorage
import com.ttasjwi.board.system.domain.member.external.db.MemberStorageImpl
import com.ttasjwi.board.system.domain.member.external.impl.ExternalPasswordHandlerImpl
import com.ttasjwi.board.system.domain.member.service.EmailVerificationStartedEventPublisher
import com.ttasjwi.board.system.domain.member.service.SocialConnectionStorage
import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.global.springsecurity.oauth2.redis.RedisOAuth2AuthorizationRequestRepository
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.TimeManager
import com.ttasjwi.board.system.global.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.integration.support.ExceptionApiTestFilter
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@Import(IntegrationTestConfig::class)
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

    protected fun generateAccessToken(
        memberId: Long = 1557L,
        role: Role = Role.USER,
        issuedAt: AppDateTime = appDateTimeFixture()
    ): AccessToken {
        return accessTokenManager.generate(authMemberFixture(memberId, role), issuedAt)
    }

    protected fun generateAccessTokenValue(
        memberId: Long = 1557L,
        role: Role = Role.USER,
        issuedAt: AppDateTime = appDateTimeFixture()
    ): String {
        return generateAccessToken(memberId, role, issuedAt).tokenValue
    }
}

class IntegrationTestConfig {

    /**
     * CustomExceptionHandleFilter 뒤에서 예외를 던지기 위한 필터
     */
    @Bean
    fun testFilter(): FilterRegistrationBean<ExceptionApiTestFilter> {
        val registration = FilterRegistrationBean<ExceptionApiTestFilter>()
        registration.filter = ExceptionApiTestFilter()

        // CustomExceptionHandleFilter (-103) 보다 뒤에 둠
        registration.order = -102
        return registration
    }

    @Primary
    @Bean
    fun timeManagerFixture(): TimeManagerFixture {
        return TimeManagerFixture()
    }
}
