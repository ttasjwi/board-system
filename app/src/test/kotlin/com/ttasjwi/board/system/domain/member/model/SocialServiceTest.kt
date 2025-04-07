package com.ttasjwi.board.system.domain.member.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialService: 소셜서비스 제공자")
class SocialServiceTest {

    @Test
    @DisplayName("GOOGLE: 구글")
    fun testGoogle() {
        // given
        // when
        val socialService = SocialService.GOOGLE

        // then
        assertThat(socialService.name).isEqualTo("GOOGLE")
    }

    @Test
    @DisplayName("KAKAO: 카카오")
    fun testKakao() {
        // given
        // when
        val socialService = SocialService.KAKAO

        // then
        assertThat(socialService.name).isEqualTo("KAKAO")
    }

    @Test
    @DisplayName("NAVER: 네이버")
    fun testNaver() {
        // given
        // when
        val socialService = SocialService.NAVER

        // then
        assertThat(socialService.name).isEqualTo("NAVER")
    }

    @Nested
    @DisplayName("Provider 이름으로부터 인스턴스를 복구한다.")
    inner class Restore {

        @Test
        @DisplayName("대소문자 구분 안 함")
        fun test1() {
            // given
            val google1 = "google"
            val google2 = "goOgle"

            // when
            val socialServiceGoogle1 = SocialService.restore(google1)
            val socialServiceGoogle2 = SocialService.restore(google2)

            // then
            assertThat(socialServiceGoogle1).isEqualTo(SocialService.GOOGLE)
            assertThat(socialServiceGoogle2).isEqualTo(SocialService.GOOGLE)
        }

        @Test
        @DisplayName("google, kakao, naver 복원 테스트")
        fun test2() {
            // given
            val google = "google"
            val kakao = "kakao"
            val naver = "naver"

            // when
            val socialServiceGoogle = SocialService.restore(google)
            val socialServiceKakao = SocialService.restore(kakao)
            val socialServiceNaver = SocialService.restore(naver)

            // then
            assertThat(socialServiceGoogle).isEqualTo(SocialService.GOOGLE)
            assertThat(socialServiceKakao).isEqualTo(SocialService.KAKAO)
            assertThat(socialServiceNaver).isEqualTo(SocialService.NAVER)
        }
    }
}
