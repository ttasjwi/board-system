package com.ttasjwi.board.system.member.domain.policy.impl

import com.ttasjwi.board.system.member.domain.exception.InvalidEmailFormatException
import com.ttasjwi.board.system.member.domain.policy.EmailFormatPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EmailFormatPolicyImplTest {

    private lateinit var emailFormatPolicy: EmailFormatPolicy

    @BeforeEach
    fun setup() {
        emailFormatPolicy = EmailFormatPolicyImpl()
    }

    @Test
    @DisplayName("올바른 로컬파트@도메인으로 구성되어 있을 때는 원본 문자열을 담은 Result 반환")
    fun testFormatValid() {
        val value = "local@domain.org"
        val result = emailFormatPolicy.validate(value)

        val email = result.getOrThrow()

        assertThat(result.isSuccess).isTrue()
        assertThat(email).isEqualTo(value)
    }

    @Test
    @DisplayName("온점이 연속으로 두개 포함된 경우 false 를 반환")
    fun testDoubleDot() {
        val value = "ttasj..wi@gmai.com"
        val result = emailFormatPolicy.validate(value)
        val ex = assertThrows<InvalidEmailFormatException> {
            result.getOrThrow()
        }

        assertThat(result.isFailure).isTrue()
        assertThat(ex.args[0]).isEqualTo(value)
        assertThat(ex.message).isEqualTo("이메일의 형식이 올바르지 않습니다. (email = $value)")
    }

    @Test
    @DisplayName("@가 없는 문자열은 이메일 검증시 false 를 반환")
    fun testNoAt() {
        val value = "ttasjwigmail.com"
        val result = emailFormatPolicy.validate(value)

        val ex = assertThrows<InvalidEmailFormatException> {
            result.getOrThrow()
        }
        assertThat(result.isFailure).isTrue()
        assertThat(ex.args[0]).isEqualTo(value)
        assertThat(ex.message).isEqualTo("이메일의 형식이 올바르지 않습니다. (email = $value)")
    }


    @Test
    @DisplayName("@가 2개 이상 있는 문자열은 이메일 검증시 false 를 반환")
    fun testDoubleAt() {
        val value = "ttasjwi@gmail@hello.com"
        val result = emailFormatPolicy.validate(value)

        val ex = assertThrows<InvalidEmailFormatException> {
            result.getOrThrow()
        }
        assertThat(result.isFailure).isTrue()
        assertThat(ex.args[0]).isEqualTo(value)
        assertThat(ex.message).isEqualTo("이메일의 형식이 올바르지 않습니다. (email = $value)")
    }
}
