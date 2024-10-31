package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.member.domain.exception.InvalidNicknameFormatException
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.service.NicknameCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


@DisplayName("NicknameCreatorImpl: 닉네임 인스턴스 생성을 책임지는 도메인 서비스")
class NicknameCreatorImplTest {

    private lateinit var nicknameCreator: NicknameCreator

    @BeforeEach
    fun setup() {
        nicknameCreator = NicknameCreatorImpl()
    }


    @Nested
    @DisplayName("create(): 닉네임을 생성하고 그 결과를 Result에 담아 반환한다.")
    inner class Create {

        @ParameterizedTest
        @ValueSource(strings = ["asdf", "1가2나3다", "0A1a2ab", "1가345", "4ㅏ기꾼"])
        @DisplayName("한글, 영어, 숫자를 허용한다")
        fun testRightFormat(value: String) {
            val result = nicknameCreator.create(value)
            val nickname = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(nickname).isNotNull
            assertThat(nickname.value).isEqualTo(value)
        }


        @Test
        @DisplayName("길이 유효성: ${Nickname.MIN_LENGTH}자 이상, ${Nickname.MAX_LENGTH}자 이하")
        fun testSuccessLength() {
            val minLengthValue = "a".repeat(Nickname.MIN_LENGTH)
            val maxLengthValue = "a".repeat(Nickname.MAX_LENGTH)

            val minLengthResult = nicknameCreator.create(minLengthValue)
            val maxLengthResult = nicknameCreator.create(maxLengthValue)

            assertThat(minLengthResult.isSuccess).isTrue()
            assertThat(maxLengthResult.isSuccess).isTrue()

            val minLengthNickname = minLengthResult.getOrThrow()
            val maxLengthNickname = maxLengthResult.getOrThrow()

            assertThat(minLengthNickname.value).isEqualTo(minLengthValue)
            assertThat(maxLengthNickname.value).isEqualTo(maxLengthValue)
        }

        @Test
        @DisplayName("최소 글자수보다 글자수가 적으면 예외가 발생한다.")
        fun testFailureLength1() {
            val tooShortLengthValue = "a".repeat(Nickname.MIN_LENGTH - 1)
            val tooShortLengthNicknameResult = nicknameCreator.create(tooShortLengthValue)

            assertThat(tooShortLengthNicknameResult.isFailure).isTrue()
            val exception = assertThrows<InvalidNicknameFormatException> { tooShortLengthNicknameResult.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(tooShortLengthValue)
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun testFailureLength2() {
            val tooLongLengthValue = "a".repeat(Nickname.MAX_LENGTH + 1)
            val tooLongLengthNicknameResult = nicknameCreator.create(tooLongLengthValue)

            assertThat(tooLongLengthNicknameResult.isFailure).isTrue()
            val exception = assertThrows<InvalidNicknameFormatException> { tooLongLengthNicknameResult.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(tooLongLengthValue)
        }


        @ParameterizedTest
        @ValueSource(strings = ["a badfc", "1 as가f4", "     "])
        @DisplayName("공백은 허용되지 않음. 포함되면 생성 시 예외 발생")
        fun testSpace(value: String) {
            val result = nicknameCreator.create(value)
            val exception = assertThrows<InvalidNicknameFormatException> { result.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(value)
        }


        @ParameterizedTest
        @ValueSource(strings = ["!aas", "122!", "23da@", "<12334", "_1236a"])
        @DisplayName("특수문자는 사용할 수 없다. 포함되면 생성 시 예외 발생")
        fun testSpecialCharacter(value: String) {
            val result = nicknameCreator.create(value)
            val exception = assertThrows<InvalidNicknameFormatException> { result.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(value)
        }

    }
}
