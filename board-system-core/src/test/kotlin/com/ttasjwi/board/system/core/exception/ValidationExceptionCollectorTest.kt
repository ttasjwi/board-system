package com.ttasjwi.board.system.core.exception

import com.ttasjwi.board.system.core.exception.fixture.customExceptionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("ValidationExceptionCollector: 여러 개의 입력값 검증예외를 군집으로 모아 관리하리하기 위한 목적으로 정의한 특수 예외")
class ValidationExceptionCollectorTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        // given
        // when
        val exception = ValidationExceptionCollector()

        // then
        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArguments")
        assertThat(exception.args).isEmpty()
        assertThat(exception.source).isEqualTo("*")
        assertThat(exception.debugMessage).isEqualTo("입력값 유효성 검증에 실패했습니다.")
        assertThat(exception.cause).isNull()
    }


    @Nested
    @DisplayName("getExceptions: 가지고 있는 커스텀 예외 목록을 변경 불가능한 리스트로 반환한다.")
    inner class GetExceptions {


        @Test
        @DisplayName("예외가 추가되어 있지 않으면 빈 리스트가 반환된다.")
        fun testEmpty() {
            // given
            val exceptionCollector = ValidationExceptionCollector()

            // when
            val exceptions: List<CustomException> = exceptionCollector.getExceptions()

            // then
            assertThat(exceptions).isEmpty()
        }

        @Test
        fun testNotEmpty() {
            // given
            val exceptionCollector = ValidationExceptionCollector()
            val exception = customExceptionFixture()
            exceptionCollector.addCustomExceptionOrThrow(exception)

            // when
            val exceptions: List<CustomException> = exceptionCollector.getExceptions()

            // then
            assertThat(exceptions).containsExactly(exception)
        }
    }


    @Nested
    @DisplayName("addCustomExceptionOrThrow : 커스텀 예외는 수집하고, 커스텀 예외가 아니면 throw 한다.")
    inner class AddCustomExceptionOrThrow {

        @Test
        @DisplayName("커스텀 예외를 추가하고, getExceptions 를 통해 예외 목록을 추출할 수 있다.")
        fun addAndGetTest() {
            // given
            val exception1 = customExceptionFixture(
                debugMessage = "some debug message1"
            )

            val exception2 = customExceptionFixture(
                debugMessage = "some debug message2"
            )

            val exceptionCollector = ValidationExceptionCollector()
            exceptionCollector.addCustomExceptionOrThrow(exception1)
            exceptionCollector.addCustomExceptionOrThrow(exception2)

            val exceptions = exceptionCollector.getExceptions()

            assertThat(exceptions.size).isEqualTo(2)
            assertThat(exceptions).containsExactly(exception1, exception2)
        }

        @Test
        @DisplayName("커스텀 예외가 아닌 것을 추가하면 예외가 던져진다.")
        fun throwTest() {
            val runtimeException = IllegalStateException("something is wrong")

            val exceptionCollector = ValidationExceptionCollector()

            assertThrows<IllegalStateException> {
                exceptionCollector.addCustomExceptionOrThrow(runtimeException)
            }
        }
    }

    @Nested
    @DisplayName("throwIfNotEmpty: 내부에 예외가 있으면 자기 자신을 throw 한다.")
    inner class ThrowIfNotEmpty {

        @Test
        @DisplayName("예외가 있으면 자기 자신을 throw 한다.")
        fun throwIfNotEmptyTest1() {
            val exception = customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.code1",
                args = listOf("1", "2"),
                source = "?",
                debugMessage = "some debug message1"
            )

            val exceptionCollector = ValidationExceptionCollector()
            exceptionCollector.addCustomExceptionOrThrow(exception)

            val thrownException = assertThrows<ValidationExceptionCollector> {
                exceptionCollector.throwIfNotEmpty()
            }
            assertThat(thrownException).isEqualTo(exceptionCollector)
        }

        @Test
        @DisplayName("예외가 없으면 에외가 throw 되지 않는다.")
        fun throwIfNotEmptyTest2() {
            val exceptionCollector = ValidationExceptionCollector()
            assertDoesNotThrow { exceptionCollector.throwIfNotEmpty() }
        }
    }


    @Nested
    @DisplayName("isNotEmpty(): 수집된 커스텀 예외가 있는 지 여부를 반환한다.")
    inner class IsNotEmpty {

        @Test
        @DisplayName("수집된 커스텀 예외가 있으면 true 를 반환한다.")
        fun test1() {
            val exceptionCollector = ValidationExceptionCollector()
            val exception = customExceptionFixture()
            exceptionCollector.addCustomExceptionOrThrow(exception)

            assertThat(exceptionCollector.isNotEmpty()).isTrue()
        }

        @Test
        @DisplayName("수집된 커스텀 예외가 없으면 false 를 반환한다.")
        fun test2() {
            val exceptionCollector = ValidationExceptionCollector()
            assertThat(exceptionCollector.isNotEmpty()).isFalse()
        }
    }

}
