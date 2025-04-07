package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.global.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("PasswordManagerFixture 테스트")
class PasswordManagerFixtureTest {

    private lateinit var passwordManager: PasswordManagerFixture

    @BeforeEach
    fun setup() {
        passwordManager = PasswordManagerFixture()
    }

    @Nested
    @DisplayName("createRawPassword: RawPassword 를 생성하고 그 결과를 Result 로 감싸 반환한다")
    inner class CreateRawPassword {

        @Test
        @DisplayName("성공테스트 - 원본값과 같은 RawPassword를 생성한다")
        fun success() {
            val value = "1234"
            val rawPassword = passwordManager.validateRawPassword(value).getOrThrow()

            assertThat(rawPassword).isEqualTo(value)
        }

        @Test
        @DisplayName("실패테스트 - ${PasswordManagerFixture.ERROR_PASSWORD} 가 입력일 경우 예외가 발생한다")
        fun failure() {
            val value = PasswordManagerFixture.ERROR_PASSWORD
            val result = passwordManager.validateRawPassword(value)

            val exception = result.exceptionOrNull() as CustomException

            assertThat(result.isFailure).isTrue()
            assertThat(exception.source).isEqualTo("password")
            assertThat(exception.debugMessage).isEqualTo("패스워드 포맷 예외 - 픽스쳐")
        }
    }

    @Nested
    @DisplayName("createRandomRawPassword: 랜덤 패스워드를 생성한다.")
    inner class CreateRandomRawPassword {

        @Test
        @DisplayName("랜덤한 패스워드가 생성되는 지 확인")
        fun test() {
            val password = passwordManager.createRandomRawPassword()
            assertThat(password.length).isEqualTo(PasswordManagerFixture.RANDOM_PASSWORD_LENGTH)
        }
    }

    @Nested
    @DisplayName("encode: 패스워드를 인코딩한다")
    inner class Encode {

        @Test
        @DisplayName("encode: 패스워드를 인코딩한다. 이 때 인코딩된 값은 원본 값과 같다.")
        fun test() {
            val rawPassword = "1234"
            val encodedPassword = passwordManager.encode(rawPassword)
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
            val encodedPassword = passwordManager.encode(rawPassword)

            // when
            val matches = passwordManager.matches(rawPassword, encodedPassword)

            // then
            assertThat(matches).isTrue()
        }

        @Test
        @DisplayName("원본 패스워드가 다르면, false를 반환한다.")
        fun testDifferentPassword() {
            // given
            val rawPassword = "1234"
            val encodedPassword = passwordManager.encode(rawPassword)

            // when
            val matches = passwordManager.matches("1235", encodedPassword)

            // then
            assertThat(matches).isFalse()
        }
    }
}
