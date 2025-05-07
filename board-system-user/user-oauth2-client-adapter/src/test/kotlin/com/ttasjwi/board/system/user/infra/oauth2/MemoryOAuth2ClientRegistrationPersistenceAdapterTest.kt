package com.ttasjwi.board.system.user.infra.oauth2

import com.ttasjwi.board.system.user.infra.test.OAuth2AuthorizationClientAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[user-oauth2-client-adapter] MemoryOAuth2ClientRegistrationPersistenceAdapter 테스트")
class MemoryOAuth2ClientRegistrationPersistenceAdapterTest : OAuth2AuthorizationClientAdapterTest() {

    @Test
    @DisplayName("naver, kakao, google 조회 테스트")
    fun testFound1() {
        assertThat(oAuth2ClientRegistrationPersistenceAdapter.findById("naver")).isNotNull
        assertThat(oAuth2ClientRegistrationPersistenceAdapter.findById("kakao")).isNotNull
        assertThat(oAuth2ClientRegistrationPersistenceAdapter.findById("google")).isNotNull
    }

    @Test
    @DisplayName("naver, kakao, google 조회 테스트 (대소문자 구분 안함)")
    fun testFound2() {
        assertThat(oAuth2ClientRegistrationPersistenceAdapter.findById("NaVer")).isNotNull
        assertThat(oAuth2ClientRegistrationPersistenceAdapter.findById("KAKAO")).isNotNull
        assertThat(oAuth2ClientRegistrationPersistenceAdapter.findById("googLE")).isNotNull
    }

    @Test
    @DisplayName("naver, kakao, google 이 아닌 아이디로 조회하면 null 반환 테스트")
    fun testNotFound() {
        assertThat(oAuth2ClientRegistrationPersistenceAdapter.findById("facebook")).isNull()
    }
}
