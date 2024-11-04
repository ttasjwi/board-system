package com.ttasjwi.board.system.core.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
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
