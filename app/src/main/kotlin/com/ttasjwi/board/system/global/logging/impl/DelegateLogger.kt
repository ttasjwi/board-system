package com.ttasjwi.board.system.global.logging.impl

import com.ttasjwi.board.system.global.logging.Logger
import io.github.oshai.kotlinlogging.KotlinLogging

internal class DelegateLogger(clazz: Class<*>) : Logger {

    private val target = KotlinLogging.logger(clazz.name)

    override fun trace(message: () -> Any?) {
        target.trace(message)
    }

    override fun trace(throwable: Throwable, message: () -> Any?) {
        target.trace(throwable, message)
    }

    override fun trace(throwable: Throwable) {
        target.trace(throwable) {}
    }

    override fun debug(message: () -> Any?) {
        target.debug(message)
    }

    override fun debug(throwable: Throwable, message: () -> Any?) {
        target.debug(throwable, message)
    }

    override fun debug(throwable: Throwable) {
        target.debug(throwable) { }
    }

    override fun info(message: () -> Any?) {
        target.info(message)
    }

    override fun info(throwable: Throwable, message: () -> Any?) {
        target.info(throwable, message)
    }

    override fun info(throwable: Throwable) {
        target.info(throwable) { }
    }

    override fun warn(message: () -> Any?) {
        target.warn(message)
    }

    override fun warn(throwable: Throwable, message: () -> Any?) {
        target.warn(throwable, message)
    }

    override fun warn(throwable: Throwable) {
        target.warn(throwable) {}
    }

    override fun error(message: () -> Any?) {
        target.error(message)
    }

    override fun error(throwable: Throwable, message: () -> Any?) {
        target.error(throwable, message)
    }

    override fun error(throwable: Throwable) {
        target.error(throwable) {}
    }
}
