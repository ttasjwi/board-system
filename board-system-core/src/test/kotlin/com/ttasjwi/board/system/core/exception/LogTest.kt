package com.ttasjwi.board.system.core.exception

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class LogTest {

    companion object {

        private val log = LoggerFactory.getLogger(LogTest::class.java)
    }

    @Test
    fun doLog() {
        log.info("hello")
    }
}
