package com.ttasjwi.board.system.user.domain.policy

import com.ttasjwi.board.system.user.domain.exception.InvalidNicknameFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("NicknamePolicyImpl: 닉네임 관련 정책, 기능을 책임지는 도메인 서비스")
class NicknamePolicyImplTest {

    private lateinit var nicknamePolicy: NicknamePolicy

    @BeforeEach
    fun setup() {
        nicknamePolicy = NicknamePolicyImpl()
    }

    @Nested
    @DisplayName("validate(): 닉네임 문자열을 검증하고 그 결과를 Result에 담아 반환한다.")
    inner class Validate {

        @ParameterizedTest
        @ValueSource(strings = ["asdf", "1가2나3다", "0A1a2ab", "1가345", "4ㅏ기꾼"])
        @DisplayName("한글, 영어, 숫자를 허용한다")
        fun testRightFormat(value: String) {
            val result = nicknamePolicy.validate(value)
            val nickname = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(nickname).isNotNull
            assertThat(nickname).isEqualTo(value)
        }


        @Test
        @DisplayName("길이 유효성: ${NicknamePolicyImpl.MIN_LENGTH}자 이상, ${NicknamePolicyImpl.MAX_LENGTH}자 이하")
        fun testSuccessLength() {
            val minLengthValue = "a".repeat(NicknamePolicyImpl.MIN_LENGTH)
            val maxLengthValue = "a".repeat(NicknamePolicyImpl.MAX_LENGTH)

            val minLengthResult = nicknamePolicy.validate(minLengthValue)
            val maxLengthResult = nicknamePolicy.validate(maxLengthValue)

            assertThat(minLengthResult.isSuccess).isTrue()
            assertThat(maxLengthResult.isSuccess).isTrue()

            val minLengthNickname = minLengthResult.getOrThrow()
            val maxLengthNickname = maxLengthResult.getOrThrow()

            assertThat(minLengthNickname).isEqualTo(minLengthValue)
            assertThat(maxLengthNickname).isEqualTo(maxLengthValue)
        }

        @Test
        @DisplayName("최소 글자수보다 글자수가 적으면 예외가 발생한다.")
        fun testFailureLength1() {
            val tooShortLengthValue = "a".repeat(NicknamePolicyImpl.MIN_LENGTH - 1)
            val tooShortLengthNicknameResult = nicknamePolicy.validate(tooShortLengthValue)

            assertThat(tooShortLengthNicknameResult.isFailure).isTrue()
            val exception = assertThrows<InvalidNicknameFormatException> { tooShortLengthNicknameResult.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(tooShortLengthValue)
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun testFailureLength2() {
            val tooLongLengthValue = "a".repeat(NicknamePolicyImpl.MAX_LENGTH + 1)
            val tooLongLengthNicknameResult = nicknamePolicy.validate(tooLongLengthValue)

            assertThat(tooLongLengthNicknameResult.isFailure).isTrue()
            val exception = assertThrows<InvalidNicknameFormatException> { tooLongLengthNicknameResult.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(tooLongLengthValue)
        }


        @ParameterizedTest
        @ValueSource(strings = ["a badfc", "1 as가f4", "     "])
        @DisplayName("공백은 허용되지 않음. 포함되면 생성 시 예외 발생")
        fun testSpace(value: String) {
            val result = nicknamePolicy.validate(value)
            val exception = assertThrows<InvalidNicknameFormatException> { result.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(value)
        }


        @ParameterizedTest
        @ValueSource(strings = ["!aas", "122!", "23da@", "<12334", "_1236a"])
        @DisplayName("특수문자는 사용할 수 없다. 포함되면 생성 시 예외 발생")
        fun testSpecialCharacter(value: String) {
            val result = nicknamePolicy.validate(value)
            val exception = assertThrows<InvalidNicknameFormatException> { result.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(value)
        }

    }

    @Nested
    @DisplayName("createRandom : 호출하면 랜덤한 닉네임이 생성된다.")
    inner class CreateRandom {

        @Test
        @DisplayName("랜덤 닉네임이 생성되고 길이는 ${NicknamePolicyImpl.RANDOM_NICKNAME_LENGTH} 이다.")
        fun test() {
            val nickname = nicknamePolicy.createRandom()
            assertThat(nickname.length).isEqualTo(NicknamePolicyImpl.RANDOM_NICKNAME_LENGTH)
        }
    }
}
