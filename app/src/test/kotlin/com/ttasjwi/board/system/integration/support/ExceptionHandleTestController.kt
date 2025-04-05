package com.ttasjwi.board.system.integration.support

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.global.logging.getLogger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 예외 처리 동작 확인을 위한 컨트롤러
 */
@RestController
class ExceptionHandleTestController {

    companion object {
        private val log = getLogger(ExceptionHandleTestController::class.java)
    }

    @GetMapping("/api/v1/test/throw-ex")
    fun throwException(): String {
        log.info { "컨트롤러에서 예외를 발생시킵니다" }
        throw customExceptionFixture(
            code = "Error.Fixture",
            source = "controller",
            status = ErrorStatus.BAD_REQUEST,
            args = emptyList(),
            debugMessage = "컨트롤러에서 발생한 예외"
        )
    }

    @GetMapping("/api/v1/test/no-ex")
    fun success(): String {
        return "hello"
    }
}
