package com.ttasjwi.board.system.member.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("usernameFixture(): 사용자아이디(Username)의 픽스쳐 생성")
class UsernameFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도, 기본값 value 를 가지고 있다.")
    fun test1() {
        val testUsername = usernameFixture()
        assertThat(testUsername.value).isNotNull()
    }

    @Test
    @DisplayName("호출 시 전달한 사용자 아이디 값을 value 로 갖고 있다.")
    fun test2() {
        val value = "ttasjwi"
        val testUsername = usernameFixture(value)

        assertThat(testUsername.value).isEqualTo(value)
    }
}
