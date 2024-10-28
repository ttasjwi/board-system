package com.ttasjwi.board.system.core.application

interface TransactionRunner {
    fun <T> run(function: () -> T): T
    fun <T> runReadOnly(function: () -> T): T
}
