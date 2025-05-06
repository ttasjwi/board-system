package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.fixture.googleOAuth2ClientRegistrationFixture
import com.ttasjwi.board.system.user.domain.model.fixture.kakaoOAuth2ClientRegistrationFixgture
import com.ttasjwi.board.system.user.domain.model.fixture.naverOAuth2ClientRegistrationFixture
import com.ttasjwi.board.system.user.domain.port.OAuth2ClientRegistrationPersistencePort

class OAuth2ClientRegistrationPersistencePortFixture : OAuth2ClientRegistrationPersistencePort {

    private val storage = mapOf(
        "naver" to  naverOAuth2ClientRegistrationFixture(),
        "kakao"  to kakaoOAuth2ClientRegistrationFixgture(),
        "google" to  googleOAuth2ClientRegistrationFixture(),
    )

    override fun findById(registrationId: String): OAuth2ClientRegistration? {
        return storage[registrationId]
    }
}
