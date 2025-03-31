package com.ttasjwi.board.system.member.domain.external.impl

import com.ttasjwi.board.system.member.domain.external.ExternalEmailFormatChecker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("외부 이메일 검증기(ApacheEmailFormatCheckerImpl) 테스트")
class ApacheEmailFormatCheckerImplTest {

    private lateinit var externalEmailFormatChecker: ExternalEmailFormatChecker

    @BeforeEach
    fun setup() {
        externalEmailFormatChecker = ApacheEmailFormatCheckerImpl()
    }

    @Test
    @DisplayName("올바른 로컬파트@도메인으로 구성되어 있을 때 true 를 반환")
    fun testFormatValid() {
        val email = "local@domain.org"
        val isValid = externalEmailFormatChecker.isValidFormatEmail(email)
        assertThat(isValid).isTrue()
    }

    @Test
    @DisplayName("온점이 연속으로 두개 포함된 경우 false 를 반환")
    fun testDoubleDot() {
        val email = "ttasj..wi@gmai.com"
        val isValid = externalEmailFormatChecker.isValidFormatEmail(email)

        assertThat(isValid).isFalse()
    }

    @Test
    @DisplayName("@가 없는 문자열은 이메일 검증시 false 를 반환")
    fun testNoAt() {
        val email = "ttasjwigmail.com"
        val isValid = externalEmailFormatChecker.isValidFormatEmail(email)
        assertThat(isValid).isFalse()
    }


    @Test
    @DisplayName("@가 2개 이상 있는 문자열은 이메일 검증시 false 를 반환")
    fun testDoubleAt() {
        val email = "ttasjwi@gmail@hello.com"
        val isValid = externalEmailFormatChecker.isValidFormatEmail(email)

        assertThat(isValid).isFalse()
    }

}
