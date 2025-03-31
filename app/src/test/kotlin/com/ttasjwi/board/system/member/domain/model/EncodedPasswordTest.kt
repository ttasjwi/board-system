package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.model.fixture.encodedPasswordFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("EncodedPassword : 인코딩 된 패스워드")
class EncodedPasswordTest {

    @Nested
    @DisplayName("restore : 값으로부터 EncodedPassword 를 복원한다.")
    inner class Restore {

        @Test
        @DisplayName("값을 전달하면 그 값을 기반으로 EncodedPassword를 복원한다.")
        fun test() {
            val value = "1234"
            val restoredPassword = EncodedPassword.restore(value)

            assertThat(restoredPassword).isNotNull
            assertThat(restoredPassword.value).isEqualTo(value)
        }
    }

    @Test
    @DisplayName("toString: 패스워드 문자열을 디버깅용으로 출력하려 시도하면, 암호는 노출되지 않는다.")
    fun testToString() {
        val password = encodedPasswordFixture("1234")
        assertThat(password.toString()).isEqualTo("EncodedPassword(value=[SECRET])")
    }
}
