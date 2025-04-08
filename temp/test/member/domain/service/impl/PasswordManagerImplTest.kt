package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.member.domain.exception.InvalidPasswordFormatException
import com.ttasjwi.board.system.member.domain.external.ExternalPasswordHandler
import com.ttasjwi.board.system.member.domain.external.fixture.ExternalPasswordHandlerFixture
import com.ttasjwi.board.system.member.domain.service.PasswordManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("PasswordManagerImpl: 패스워드 포맷 검증, 인코딩, 매칭을 담당한다")
class PasswordManagerImplTest {

    private lateinit var passwordManager: PasswordManager
    private lateinit var externalPasswordHandler: ExternalPasswordHandler

    @BeforeEach
    fun setup() {
        externalPasswordHandler = ExternalPasswordHandlerFixture()
        passwordManager = PasswordManagerImpl(externalPasswordHandler)
    }

    @Nested
    @DisplayName("validateRawPassword : 원본 패스워드 문자열 포맷을 검증하고 결과를 Result 로 담아 반환한다.")
    inner class ValidateRawPassword {

        @Test
        @DisplayName("길이 유효성: ${PasswordManagerImpl.MIN_LENGTH}자 이상, ${PasswordManagerImpl.MAX_LENGTH}자 이하")
        fun testSuccess() {
            val minLengthString = "a".repeat(PasswordManagerImpl.MIN_LENGTH)
            val maxLengthString = "a".repeat(PasswordManagerImpl.MAX_LENGTH)


            val minLengthPasswordResult = passwordManager.validateRawPassword(minLengthString)
            val maxLengthPasswordResult = passwordManager.validateRawPassword(maxLengthString)

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
            val tooShortLengthString = "a".repeat(PasswordManagerImpl.MIN_LENGTH - 1)

            val tooShortLengthPasswordResult = passwordManager.validateRawPassword(tooShortLengthString)

            assertThat(tooShortLengthPasswordResult.isFailure).isTrue()
            assertThrows<InvalidPasswordFormatException> { tooShortLengthPasswordResult.getOrThrow() }
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun testFailure() {
            val tooLongLengthString = "a".repeat(PasswordManagerImpl.MAX_LENGTH + 1)

            val tooLongLengthPasswordResult = passwordManager.validateRawPassword(tooLongLengthString)

            assertThat(tooLongLengthPasswordResult.isFailure).isTrue()
            assertThrows<InvalidPasswordFormatException> { tooLongLengthPasswordResult.getOrThrow() }
        }
    }

    @Nested
    @DisplayName("createRandomRawPassword: 랜덤 패스워드를 생성한다.")
    inner class CreateRandomRawPassword {

        @Test
        @DisplayName("랜덤한 패스워드가 생성되는 지 확인")
        fun test() {
            val password = passwordManager.createRandomRawPassword()
            assertThat(password.length).isEqualTo(PasswordManagerImpl.RANDOM_PASSWORD_LENGTH)
        }
    }

    @Nested
    @DisplayName("encode: RawPassword 의 값을 인코딩한다.")
    inner class Encode {

        @Test
        @DisplayName("외부 패스워드 처리기를 통해 인코딩한다.")
        fun test() {
            val rawPassword = "1234"
            val encodedPassword = passwordManager.encode(rawPassword)
            assertThat(encodedPassword).isNotNull
            assertThat(encodedPassword).isEqualTo(rawPassword)
        }
    }

    @Nested
    @DisplayName("matches: RawPassword 와 EncodedPassword 를 매칭시켜서 서로 맞는 지 여부를 반환한다.")
    inner class Matches {

        @Test
        @DisplayName("외부 패스워드 인코더를 통해 매칭을 위임하고 그 결과를 반환한다.")
        fun test() {
            val rawPassword = "1234"
            val encodedPassword = "1234"
            assertThat(passwordManager.matches(rawPassword, encodedPassword)).isTrue()
            assertThat(passwordManager.matches("1235", encodedPassword)).isFalse()
        }
    }
}
