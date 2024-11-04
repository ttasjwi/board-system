package com.ttasjwi.board.system.core.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(value = ["test"])
@DisplayName("TransactionManagerImpl 테스트")
class TransactionRunnerImplTest @Autowired constructor(
    private val transactionRunner: TransactionRunner
) {

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
