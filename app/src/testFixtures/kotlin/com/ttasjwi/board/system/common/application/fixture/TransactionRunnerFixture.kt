package com.ttasjwi.board.system.common.application.fixture

import com.ttasjwi.board.system.common.application.TransactionRunner

class TransactionRunnerFixture : TransactionRunner {

    override fun <T> run(function: () -> T): T {
        return function.invoke()
    }

    override fun <T> runReadOnly(function: () -> T): T {
        return function.invoke()
    }
}
