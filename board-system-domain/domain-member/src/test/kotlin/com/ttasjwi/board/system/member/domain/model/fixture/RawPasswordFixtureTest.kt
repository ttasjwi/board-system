package com.ttasjwi.board.system.member.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("rawPasswordFixture(): 테스트용 RawPassword 를 편리하게 생성하도록 하기 위한 Fixture")
class RawPasswordFixtureTest {

    @Test
    @DisplayName("인자를 지정하지 않아도 기본값을 가진다.")
    fun test() {
        val rawPassword = rawPasswordFixture()
        assertThat(rawPassword.value).isNotNull()
    }

    @Test
    @DisplayName("인자를 지정하면 해당 값을 value 로 가진다.")
    fun test2() {
        val value = "2341@!"
        val rawPassword = rawPasswordFixture(value)
        assertThat(rawPassword.value).isEqualTo(value)
    }
}
