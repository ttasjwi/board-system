package com.ttasjwi.board.system.member.domain.policy.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.member.domain.policy.fixturer.PasswordPolicyFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("PasswordPolicyFixture 테스트")
class PasswordPolicyFixtureTest {

    private lateinit var passwordPolicyFixture: PasswordPolicyFixture

    @BeforeEach
    fun setup() {
        passwordPolicyFixture = PasswordPolicyFixture()
    }

    @Nested
    @DisplayName("validate: Password 를 검증하고 그 결과를 Result 로 감싸 반환한다")
    inner class Validate {

        @Test
        @DisplayName("성공테스트 - 원본값과 같은 RawPassword를 생성한다")
        fun success() {
            val value = "1234"
            val rawPassword = passwordPolicyFixture.validate(value).getOrThrow()

            assertThat(rawPassword).isEqualTo(value)
        }

        @Test
        @DisplayName("실패테스트 - ${PasswordPolicyFixture.ERROR_PASSWORD} 가 입력일 경우 예외가 발생한다")
        fun failure() {
            val value = PasswordPolicyFixture.ERROR_PASSWORD
            val result = passwordPolicyFixture.validate(value)

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
            val password = passwordPolicyFixture.createRandomPassword()
            assertThat(password.length).isEqualTo(PasswordPolicyFixture.RANDOM_PASSWORD_LENGTH)
        }
    }
}
