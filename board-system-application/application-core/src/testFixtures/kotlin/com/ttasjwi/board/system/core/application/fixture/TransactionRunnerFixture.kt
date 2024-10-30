package com.ttasjwi.board.system.core.application.fixture

import com.ttasjwi.board.system.core.application.TransactionRunner

class TransactionRunnerFixture : TransactionRunner {

    override fun <T> run(function: () -> T): T {
        return function.invoke()
    }

    override fun <T> runReadOnly(function: () -> T): T {
        return function.invoke()
    }
}
