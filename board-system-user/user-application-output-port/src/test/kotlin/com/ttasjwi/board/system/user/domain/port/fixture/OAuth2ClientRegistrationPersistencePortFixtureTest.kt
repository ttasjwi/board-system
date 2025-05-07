package com.ttasjwi.board.system.user.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OAuth2ClientRegistrationPersistencePortFixtureTest {

    private lateinit var portFixture: OAuth2ClientRegistrationPersistencePortFixture

    @BeforeEach
    fun setUp() {
        portFixture = OAuth2ClientRegistrationPersistencePortFixture()
    }

    @Test
    @DisplayName("naver, kakao, google 픽스쳐 조회 테스트")
    fun testFound1() {
        assertThat(portFixture.findById("naver")).isNotNull
        assertThat(portFixture.findById("kakao")).isNotNull
        assertThat(portFixture.findById("google")).isNotNull
    }

    @Test
    @DisplayName("naver, kakao, google 픽스쳐 조회 테스트 (대소문자 구분 안함)")
    fun testFound2() {
        assertThat(portFixture.findById("NaVer")).isNotNull
        assertThat(portFixture.findById("KAKAO")).isNotNull
        assertThat(portFixture.findById("googLE")).isNotNull
    }

    @Test
    @DisplayName("naver, kakao, google 이 아닌 아이디로 조회하면 null 반환 테스트")
    fun testNotFound() {
        assertThat(portFixture.findById("facebook")).isNull()
    }
}
