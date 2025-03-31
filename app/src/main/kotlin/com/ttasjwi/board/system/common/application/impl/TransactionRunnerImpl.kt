package com.ttasjwi.board.system.common.application.impl

import com.ttasjwi.board.system.common.application.TransactionRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionRunnerImpl : TransactionRunner {

    @Transactional
    override fun <T> run(function: () -> T): T {
        return function.invoke()
    }

    @Transactional(readOnly = true)
    override fun <T> runReadOnly(function: () -> T): T {
        return function.invoke()
    }
}
