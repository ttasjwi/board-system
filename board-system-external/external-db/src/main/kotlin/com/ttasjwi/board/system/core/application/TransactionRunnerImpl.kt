package com.ttasjwi.board.system.core.application

import com.ttasjwi.board.system.core.annotation.component.AppComponent

@AppComponent
class TransactionRunnerImpl : TransactionRunner{

    override fun <T> run(function: () -> T): T {
        TODO("Not yet implemented")
    }

    override fun <T> runReadOnly(function: () -> T): T {
        TODO("Not yet implemented")
    }
}
