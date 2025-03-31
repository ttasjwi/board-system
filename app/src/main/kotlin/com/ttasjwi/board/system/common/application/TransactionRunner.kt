package com.ttasjwi.board.system.common.application

interface TransactionRunner {
    fun <T> run(function: () -> T): T
    fun <T> runReadOnly(function: () -> T): T
}
