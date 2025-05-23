package com.ttasjwi.board.system.user.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("PasswordEncryptionPort 픽스쳐 테스트")
class PasswordEncryptionPortFixtureTest {

    private lateinit var passwordEncryptionPortFixture: PasswordEncryptionPortFixture

    @BeforeEach
    fun setup() {
        passwordEncryptionPortFixture = PasswordEncryptionPortFixture()
    }

    @Nested
    @DisplayName("encode: 패스워드를 인코딩한다")
    inner class Encode {

        @Test
        @DisplayName("encode: 패스워드를 인코딩한다. 이 때 인코딩된 값은 원본 값과 같다.")
        fun test() {
            val rawPassword = "1234"
            val encodedPassword = passwordEncryptionPortFixture.encode(rawPassword)
            assertThat(encodedPassword).isEqualTo(rawPassword)
        }
    }

    @Nested
    @DisplayName("matches: 원본 패스워드와 인코딩 된 패스워드를 비교하여 일치하는 지 여부를 반환한다.")
    inner class Matches {

        @Test
        @DisplayName("원본 패스워드가 같으면, true를 반환한다.")
        fun testSamePassword() {
            // given
            val rawPassword = "1234"
            val encodedPassword = passwordEncryptionPortFixture.encode(rawPassword)

            // when
            val matches = passwordEncryptionPortFixture.matches(rawPassword, encodedPassword)

            // then
            assertThat(matches).isTrue()
        }

        @Test
        @DisplayName("원본 패스워드가 다르면, false를 반환한다.")
        fun testDifferentPassword() {
            // given
            val rawPassword = "1234"
            val encodedPassword = passwordEncryptionPortFixture.encode(rawPassword)

            // when
            val matches = passwordEncryptionPortFixture.matches("1235", encodedPassword)

            // then
            assertThat(matches).isFalse()
        }
    }

}
