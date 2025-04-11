package com.ttasjwi.board.system.common.websupport.test.support.controller

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.common.logger.getLogger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExceptionHandleTestController {

    companion object {
        private val log = getLogger(ExceptionHandleTestController::class.java)
    }

    @PermitAll
    @GetMapping("/api/v1/test/web-supports/exceptions/throw-ex")
    fun throwException(): Nothing {
        log.info { "컨트롤러에서 예외를 발생시킵니다" }
        throw customExceptionFixture(
            code = "Error.ExceptionHandleTestController",
            source = "ExceptionHandleTestController",
            status = ErrorStatus.BAD_REQUEST,
            args = emptyList(),
            debugMessage = "컨트롤러에서 발생한 예외"
        )
    }

    @PermitAll
    @GetMapping("/api/v1/test/web-supports/exceptions/no-ex")
    fun success(): String {
        return "hello"
    }
}
