package com.ttasjwi.board.system.user.test.config

import com.ttasjwi.board.system.user.domain.fixture.SocialLoginUseCaseFixture
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.config.OAuth2SecurityConfig
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.fixture.OAuth2AuthorizationRequestRepositoryFixture
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

@TestConfiguration
class TestOAuth2ComponentFixtureConfig {

    @Bean
    fun socialLoginUseCaseFixture(): SocialLoginUseCaseFixture {
        return SocialLoginUseCaseFixture()
    }

    @Bean
    fun oAuth2AuthorizationRequestRepositoryFixture(): AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
        return OAuth2AuthorizationRequestRepositoryFixture()
    }
}
