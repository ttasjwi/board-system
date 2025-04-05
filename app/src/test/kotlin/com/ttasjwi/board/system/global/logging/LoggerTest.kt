package com.ttasjwi.board.system.global.logging

import com.ttasjwi.board.system.global.logging.impl.DelegateLogger
import com.ttasjwi.board.system.global.logging.getLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Logger 작동 테스트 : (수동, 눈으로 확인 용도)")
internal class LoggerTest {

    private class TestClass {
        companion object {
            val log = getLogger(TestClass::class.java)
        }
    }

    @Nested
    @DisplayName("trace")
    inner class Trace {

        @Test
        @DisplayName("에러만 전달 -> 로깅레벨 info 수준 기준 -> 출력 안 됨")
        fun test1() {
            TestClass.log.trace(IllegalStateException())
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열만 전달 -> 로깅레벨 info 수준 기준 -> 출력 안 됨")
        fun test2() {
            TestClass.log.trace { "안 뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열과 에러 같이 전달 -> 로깅레벨 info 수준 기준 -> 출력 안 됨")
        fun test3() {
            TestClass.log.trace(IllegalStateException()) { "안 뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

    }

    @Nested
    @DisplayName("debug")
    inner class Debug {

        @Test
        @DisplayName("에러만 전달 -> 로깅레벨 info 수준 기준 -> 출력 안 됨")
        fun test1() {
            TestClass.log.debug(IllegalStateException())
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열만 전달 -> 로깅레벨 info 수준 기준 -> 출력 안 됨")
        fun test2() {
            TestClass.log.debug { "안 뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열과 에러 같이 전달 -> 로깅레벨 info 수준 기준 -> 출력 안 됨")
        fun test3() {
            TestClass.log.debug(IllegalStateException()) { "안 뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }
    }

    @Nested
    @DisplayName("info")
    inner class Info {
        @Test
        @DisplayName("에러만 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test1() {
            TestClass.log.info(IllegalStateException())
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열만 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test2() {
            TestClass.log.info { "뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열과 에러 같이 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test3() {
            TestClass.log.info(IllegalStateException()) { "뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

    }

    @Nested
    @DisplayName("warn")
    inner class Warn {
        @Test
        @DisplayName("에러만 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test1() {
            TestClass.log.warn(IllegalStateException())
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열만 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test2() {
            TestClass.log.warn { "뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열과 에러 같이 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test3() {
            TestClass.log.warn(IllegalStateException()) { "뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }
    }

    @Nested
    @DisplayName("error")
    inner class Error {
        @Test
        @DisplayName("에러만 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test1() {
            TestClass.log.error(IllegalStateException())
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열만 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test2() {
            TestClass.log.error { "뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }

        @Test
        @DisplayName("문자열과 에러 같이 전달 -> 로깅레벨 info 수준 기준 -> 출력 됨")
        fun test3() {
            TestClass.log.error(IllegalStateException()) { "뜬다잉" }
            assertThat(TestClass.log).isNotNull
            assertThat(TestClass.log).isInstanceOf(DelegateLogger::class.java)
        }
    }
}
