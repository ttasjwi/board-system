package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("EmailManagerFixture 테스트")
class EmailManagerFixtureTest {

    private lateinit var emailManagerFixture: EmailManagerFixture

    @BeforeEach
    fun setup() {
        emailManagerFixture = EmailManagerFixture()
    }

    @Nested
    @DisplayName("validate : 이메일 형식 검증")
    inner class Validate {

        @Test
        @DisplayName("대부분의 이메일 문자열을 검증하고, 이메일을 그대로 반환한다.")
        fun test1() {
            val value = "asdf@gmail.com"
            val result = emailManagerFixture.validate(value)
            val email = result.getOrThrow()
            assertThat(result.isSuccess).isTrue()
            assertThat(email).isEqualTo(value)
        }

        @Test
        @DisplayName("ERROR_EMAIL 과 같은 문자열의 경우 예외 발생")
        fun test2() {
            val result = emailManagerFixture.validate(EmailManagerFixture.ERROR_EMAIL)
            val ex = assertThrows<CustomException> { result.getOrThrow() }

            assertThat(result.isFailure).isTrue()
            assertThat(ex.status).isEqualTo(ErrorStatus.BAD_REQUEST)
            assertThat(ex.code).isEqualTo("Error.InvalidEmailFormat")
            assertThat(ex.args).isEmpty()
            assertThat(ex.source).isEqualTo("email")
            assertThat(ex.message).isEqualTo("이메일 포맷 예외 - 픽스쳐")
            assertThat(ex.debugMessage).isEqualTo(ex.message)
        }

    }
}
