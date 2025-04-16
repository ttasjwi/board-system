package com.ttasjwi.board.system.user.infra.crypto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PasswordEncryptionAdapterTest @Autowired constructor(
    private val passwordEncryptionAdapter: PasswordEncryptionAdapter,
) {

    @Nested
    @DisplayName("encode: 패스워드를 인코딩한다")
    inner class Encode {

        @Test
        @DisplayName("인코딩이 잘 되는 지 테스트")
        fun test() {
            val rawPassword = "1234"
            val encodedPassword = passwordEncryptionAdapter.encode(rawPassword)
            assertThat(encodedPassword).isNotNull()
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
            val encodedPassword = passwordEncryptionAdapter.encode(rawPassword)

            // when
            val matches = passwordEncryptionAdapter.matches(rawPassword, encodedPassword)

            // then
            assertThat(matches).isTrue()
        }

        @Test
        @DisplayName("원본 패스워드가 다르면, false를 반환한다.")
        fun testDifferentPassword() {
            // given
            val rawPassword = "1234"
            val encodedPassword = passwordEncryptionAdapter.encode(rawPassword)

            // when
            val matches = passwordEncryptionAdapter.matches("1235", encodedPassword)

            // then
            assertThat(matches).isFalse()
        }
    }
}
