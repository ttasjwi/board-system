package com.ttasjwi.board.system.email.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("emailFixture(): 테스트용 이메일 생성")
class EmailFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값 value 를 가지고 있다.")
    fun test1() {
        val testEmail = emailFixture()
        assertThat(testEmail.value).isNotNull()
    }

    @Test
    @DisplayName("호출시 전달한 이메일 값을 value 로 갖고 있다.")
    fun test2() {
        val value = "hello@gmail.com"
        val testEmail = emailFixture(value)

        assertThat(testEmail.value).isEqualTo(value)
    }
}
