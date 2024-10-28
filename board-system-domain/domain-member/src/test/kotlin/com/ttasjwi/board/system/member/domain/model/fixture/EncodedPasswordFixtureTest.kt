package com.ttasjwi.board.system.member.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("EncodedPasswordFixture 테스트")
class EncodedPasswordFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도, 기본값이 존재한다.")
    fun test1() {
        val testEncodedPassword = encodedPasswordFixture()
        assertThat(testEncodedPassword.value).isNotNull()
    }

    @Test
    @DisplayName("값을 지정하여 생성하면 그 값을 가지고 있다.")
    fun test2() {
        val value = "2222"
        val testEncodedPassword = encodedPasswordFixture(value)
        assertThat(testEncodedPassword.value).isEqualTo(value)
    }

}
