package com.ttasjwi.board.system.member.domain.external.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ExternalEmailFormatCheckerFixture 테스트")
class ExternalEmailFormatCheckerFixtureTest {

    @Test
    @DisplayName("대부분의 이메일 문자열을 전달하면 true를 반환한다.")
    fun test1() {
        val emailFormatChecker = ExternalEmailFormatCheckerFixture()

        val isValid = emailFormatChecker.isValidFormatEmail("asdf@gmail.com")
        assertThat(isValid).isTrue()
    }

    @Test
    @DisplayName("지정한 이메일 문자열(INVALID_FORMAT_EMAIL) 에 대해서만 false를 반환한다.")
    fun test2() {
        val emailFormatChecker = ExternalEmailFormatCheckerFixture()

        val isValid = emailFormatChecker.isValidFormatEmail(ExternalEmailFormatCheckerFixture.INVALID_FORMAT_EMAIL)
        assertThat(isValid).isFalse()
    }
}
