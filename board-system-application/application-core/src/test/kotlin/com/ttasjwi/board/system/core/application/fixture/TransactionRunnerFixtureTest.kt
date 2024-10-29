package com.ttasjwi.board.system.core.application.fixture

import com.ttasjwi.board.system.core.application.TransactionRunner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TransactionRunner 픽스쳐 테스트")
class TransactionRunnerFixtureTest {

    private lateinit var transactionRunner: TransactionRunner

    @BeforeEach
    fun setup() {
        transactionRunner = TransactionRunnerFixture()
    }

    @Test
    @DisplayName("run: 전달한 함수의 반환값을 그대로 반환한다.")
    fun testRun() {
        // given
        val function = { "hello" }

        // when
        val result = transactionRunner.run(function)

        // then
        assertThat(result).isEqualTo(function.invoke())
    }

    @Test
    @DisplayName("runReadOnly: 전달한 함수의 반환값을 그대로 반환한다.")
    fun testRunReadOnly() {
        // given
        val function = { "hello" }

        // when
        val result = transactionRunner.runReadOnly(function)

        // then
        assertThat(result).isEqualTo(function.invoke())
    }
}
