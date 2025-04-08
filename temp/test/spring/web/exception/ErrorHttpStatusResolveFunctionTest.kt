package com.ttasjwi.board.system.spring.web.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("ErrorHttpStatusResolveFunction 테스트")
class ErrorHttpStatusResolveFunctionTest {

    @Test
    @DisplayName("ErrorStatus.BAD_REQUEST 는 BAD REQUEST 상태코드로 변환한다.")
    fun caseInvalidArgument() {
        val errorStatus = ErrorStatus.BAD_REQUEST
        val httpStatus = resolveHttpStatus(errorStatus)
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    @DisplayName("ErrorStatus.NOT_FOUND 는 NOT FOUND 상태코드로 변환한다.")
    fun caseNotFound() {
        val errorStatus = ErrorStatus.NOT_FOUND
        val httpStatus = resolveHttpStatus(errorStatus)
        assertThat(httpStatus).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    @DisplayName("ErrorStatus.NOT_IMPLEMENTED 는 NOT IMPLEMENTED 상태코드로 변환한다.")
    fun caseNotImplemented() {
        val errorStatus = ErrorStatus.NOT_IMPLEMENTED
        val httpStatus = resolveHttpStatus(errorStatus)
        assertThat(httpStatus).isEqualTo(HttpStatus.NOT_IMPLEMENTED)
    }

    @Test
    @DisplayName("ErrorStatus.UNAUTHENTICATED 는 UNAUTHORIZED 상태코드로 변환한다.")
    fun caseUnauthenticated() {
        val errorStatus = ErrorStatus.UNAUTHENTICATED
        val httpStatus = resolveHttpStatus(errorStatus)
        assertThat(httpStatus).isEqualTo(HttpStatus.UNAUTHORIZED)
    }

    @Test
    @DisplayName("ErrorStatus.FORBIDDEN 은 FORBIDDEN 상태코드로 변환한다.")
    fun caseForbidden() {
        val errorStatus = ErrorStatus.FORBIDDEN
        val httpStatus = resolveHttpStatus(errorStatus)
        assertThat(httpStatus).isEqualTo(HttpStatus.FORBIDDEN)
    }

    @Test
    @DisplayName("ErrorStatus.CONFLICT 는 CONFLICT 상태코드로 변환한다.")
    fun caseConflict() {
        val errorStatus = ErrorStatus.CONFLICT
        val httpStatus = resolveHttpStatus(errorStatus)
        assertThat(httpStatus).isEqualTo(HttpStatus.CONFLICT)
    }

    @Test
    @DisplayName("ErrorStatus.APPLICATION 은 INTERNAL_SERVER_ERROR 상태코드로 변환한다.")
    fun caseApplicationError() {
        val errorStatus = ErrorStatus.APPLICATION_ERROR
        val httpStatus = resolveHttpStatus(errorStatus)
        assertThat(httpStatus).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
