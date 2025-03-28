package com.ttasjwi.board.system.common.logging

interface Logger {
    fun trace(message: () -> Any?)
    fun trace(throwable: Throwable, message: () -> Any?)
    fun trace(throwable: Throwable)

    fun debug(message: () -> Any?)
    fun debug(throwable: Throwable, message: () -> Any?)
    fun debug(throwable: Throwable)

    fun info(message: () -> Any?)
    fun info(throwable: Throwable, message: () -> Any?)
    fun info(throwable: Throwable)

    fun warn(message: () -> Any?)
    fun warn(throwable: Throwable, message: () -> Any?)
    fun warn(throwable: Throwable)

    fun error(message: () -> Any?)
    fun error(throwable: Throwable, message: () -> Any?)
    fun error(throwable: Throwable)
}
