package com.ttasjwi.board.system

import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.core.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.logging.getLogger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExceptionApiTestController {

    companion object {
        private val log = getLogger(ExceptionApiTestController::class.java)
    }


    @GetMapping("/api/test-ex")
    fun throwException(): String {
        log.info{ "컨트롤러에서 예외를 발생시킵니다" }
        throw customExceptionFixture(
            code = "Error.Fixture",
            source = "controller",
            status = ErrorStatus.BAD_REQUEST,
            args = emptyList(),
            debugMessage = "컨트롤러에서 발생한 예외"
        )
    }

    @GetMapping("/api/test-success")
    fun success(): String {
        return "hello"
    }
}
