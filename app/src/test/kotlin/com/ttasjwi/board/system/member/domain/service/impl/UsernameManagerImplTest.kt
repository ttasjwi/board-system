package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.member.domain.exception.InvalidUsernameFormatException
import com.ttasjwi.board.system.member.domain.service.UsernameManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("UsernameManagerImpl: 사용자아이디(username) 관련 정책, 기능을 책임지는 도메인 서비스")
class UsernameManagerImplTest {

    private lateinit var usernameManager: UsernameManager

    @BeforeEach
    fun setup() {
        usernameManager = UsernameManagerImpl()
    }

    @Nested
    @DisplayName("validate(): 사용자아이디(username) 을 검증하고 그 결과를 Result에 담아 반환한다.")
    inner class Validate {

        @ParameterizedTest
        @ValueSource(strings = ["aaaa", "1bbb", "012ab", "12345", "h_ello"])
        @DisplayName("영어 소문자, 숫자, 또는 언더바로만 구성되어야 한다.")
        fun testRightFormat(value: String) {

            val result = usernameManager.validate(value)
            val username = result.getOrThrow()

            assertThat(result.isSuccess).isTrue()
            assertThat(username).isNotNull
            assertThat(username).isEqualTo(value)
        }

        @Test
        @DisplayName("길이 유효성: ${UsernameManagerImpl.MIN_LENGTH}자 이상, ${UsernameManagerImpl.MAX_LENGTH}자 이하")
        fun testSuccessLength() {
            val minLengthValue = "a".repeat(UsernameManagerImpl.MIN_LENGTH)
            val maxLengthValue = "a".repeat(UsernameManagerImpl.MAX_LENGTH)

            val minLengthResult = usernameManager.validate(minLengthValue)
            val maxLengthResult = usernameManager.validate(maxLengthValue)

            assertThat(minLengthResult.isSuccess).isTrue()
            assertThat(maxLengthResult.isSuccess).isTrue()

            val minLengthUsername = minLengthResult.getOrThrow()
            val maxLengthUsername = maxLengthResult.getOrThrow()

            assertThat(minLengthUsername).isEqualTo(minLengthValue)
            assertThat(maxLengthUsername).isEqualTo(maxLengthValue)
        }

        @Test
        @DisplayName("최소 글자수보다 글자수가 적으면 예외가 발생한다.")
        fun testFailureLength1() {
            val tooShortLengthValue = "a".repeat(UsernameManagerImpl.MIN_LENGTH - 1)
            val tooShortLengthUsernameResult = usernameManager.validate(tooShortLengthValue)

            assertThat(tooShortLengthUsernameResult.isFailure).isTrue()
            val exception = assertThrows<InvalidUsernameFormatException> { tooShortLengthUsernameResult.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(tooShortLengthValue)
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun testFailureLength2() {
            val tooLongLengthValue = "a".repeat(UsernameManagerImpl.MAX_LENGTH + 1)
            val tooLongLengthUsernameResult = usernameManager.validate(tooLongLengthValue)

            assertThat(tooLongLengthUsernameResult.isFailure).isTrue()
            val exception = assertThrows<InvalidUsernameFormatException> { tooLongLengthUsernameResult.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(tooLongLengthValue)
        }


        @ParameterizedTest
        @ValueSource(strings = ["a badfc", "1 asdf4", "     "])
        @DisplayName("공백은 허용되지 않음. 포함될 경우 생성시 예외 발생")
        fun testSpace(value: String) {
            val result = usernameManager.validate(value)
            val exception = assertThrows<InvalidUsernameFormatException> { result.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(value)
        }

        @Test
        @DisplayName("대문자는 사용할 수 없다. 포함될 경우 생성시 예외 발생")
        fun testUpperCase() {
            val value = "AAAA"
            val result = usernameManager.validate(value)
            val exception = assertThrows<InvalidUsernameFormatException> { result.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(value)
        }

        @Test
        @DisplayName("한글은 사용할 수 없다. 포함될 경우 생성시 예외 발생")
        fun testKorean() {
            val value = "가aaa"
            val result = usernameManager.validate(value)
            val exception = assertThrows<InvalidUsernameFormatException> { result.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(value)
        }

        @ParameterizedTest
        @ValueSource(strings = ["!aas", "122!", "23da@"])
        @DisplayName("언더바 이외의 특수문자는 사용할 수 없다. 포함될 경우 생성시 예외 발생")
        fun testSpecialCharacter(value: String) {
            val result = usernameManager.validate(value)
            val exception = assertThrows<InvalidUsernameFormatException> { result.getOrThrow() }
            assertThat(exception.args[2]).isEqualTo(value)
        }
    }


    @Nested
    @DisplayName("createRandom : 호출하면 랜덤한 Username 문자열이 생성된다.")
    inner class CreateRandom {

        @Test
        @DisplayName("랜덤 Username 이 생성되고 길이는 ${UsernameManagerImpl.RANDOM_USERNAME_LENGTH} 이다.")
        fun test() {
            val username = usernameManager.createRandom()

            assertThat(username.length).isEqualTo(UsernameManagerImpl.RANDOM_USERNAME_LENGTH)
        }
    }
}
