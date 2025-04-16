package com.ttasjwi.board.system.user.domain.policy

import com.ttasjwi.board.system.user.domain.exception.InvalidPasswordFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("PasswordPolicyImpl: 패스워드 포맷 검증, 인코딩, 매칭을 담당한다")
class PasswordPolicyImplTest {

    private lateinit var passwordPolicy: PasswordPolicy

    @BeforeEach
    fun setup() {
        passwordPolicy = PasswordPolicyImpl()
    }

    @Nested
    @DisplayName("validate : 원본 패스워드 문자열 포맷을 검증하고 결과를 Result 로 담아 반환한다.")
    inner class Validate {

        @Test
        @DisplayName("길이 유효성: ${PasswordPolicyImpl.MIN_LENGTH}자 이상, ${PasswordPolicyImpl.MAX_LENGTH}자 이하")
        fun testSuccess() {
            val minLengthString = "a".repeat(PasswordPolicyImpl.MIN_LENGTH)
            val maxLengthString = "a".repeat(PasswordPolicyImpl.MAX_LENGTH)


            val minLengthPasswordResult = passwordPolicy.validate(minLengthString)
            val maxLengthPasswordResult = passwordPolicy.validate(maxLengthString)

            assertThat(minLengthPasswordResult.isSuccess).isTrue()
            assertThat(maxLengthPasswordResult.isSuccess).isTrue()

            val minLengthRawPassword = minLengthPasswordResult.getOrThrow()
            val maxLengthRawPassword = maxLengthPasswordResult.getOrThrow()

            assertThat(minLengthRawPassword).isEqualTo(minLengthString)
            assertThat(maxLengthRawPassword).isEqualTo(maxLengthString)
        }

        @Test
        @DisplayName("최소 글자수보다 글자수가 적으면 예외가 발생한다.")
        fun testFailure1() {
            val tooShortLengthString = "a".repeat(PasswordPolicyImpl.MIN_LENGTH - 1)

            val tooShortLengthPasswordResult = passwordPolicy.validate(tooShortLengthString)

            assertThat(tooShortLengthPasswordResult.isFailure).isTrue()
            assertThrows<InvalidPasswordFormatException> { tooShortLengthPasswordResult.getOrThrow() }
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun testFailure() {
            val tooLongLengthString = "a".repeat(PasswordPolicyImpl.MAX_LENGTH + 1)

            val tooLongLengthPasswordResult = passwordPolicy.validate(tooLongLengthString)

            assertThat(tooLongLengthPasswordResult.isFailure).isTrue()
            assertThrows<InvalidPasswordFormatException> { tooLongLengthPasswordResult.getOrThrow() }
        }
    }

    @Nested
    @DisplayName("createRandomPassword: 랜덤 패스워드를 생성한다.")
    inner class CreateRandomPassword {

        @Test
        @DisplayName("랜덤한 패스워드가 생성되는 지 확인")
        fun test() {
            val password = passwordPolicy.createRandomPassword()
            assertThat(password.length).isEqualTo(PasswordPolicyImpl.RANDOM_PASSWORD_LENGTH)
        }
    }
}
