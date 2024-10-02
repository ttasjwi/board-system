package com.ttasjwi.board.system.core.exception

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ErrorStatus: 에러 유형을 정의한 enum(열거형)")
class ErrorStatusTest {

    @Test
    @DisplayName("ErrorStatus.NOT_FOUND 는 연산의 대상을 찾지 못했음을 나타내는 에러 유형이다.")
    fun testNotFound() {
        val notFound = ErrorStatus.NOT_FOUND
        assertThat(notFound.name).isEqualTo("NOT_FOUND")
    }

    @Test
    @DisplayName("ErrorStatus.UNAUTHENTICATED 는 연산의 주체를 식별하지 못 한 상황(예: 로그인 실패, 미인증)을 나타내는 에러 유형이다.")
    fun testUnauthenticated() {
        val unAuthenticated = ErrorStatus.UNAUTHENTICATED
        assertThat(unAuthenticated.name).isEqualTo("UNAUTHENTICATED")
    }

    @Test
    @DisplayName("ErrorStatus.INVALID_ARGUMENT 는 연산에 전달한 값이 유효성에 맞지 않을 때를 나타내는 에러 유형이다.")
    fun testInvalidArgument() {
        val invalidArgument = ErrorStatus.INVALID_ARGUMENT
        assertThat(invalidArgument.name).isEqualTo("INVALID_ARGUMENT")
    }

    @Test
    @DisplayName("ErrorStatus.FORBIDDEN 은 연산을 수행할 권한이 없음을 나타내는 에러 유형이다.")
    fun testForbidden() {
        val forbidden = ErrorStatus.FORBIDDEN
        assertThat(forbidden.name).isEqualTo("FORBIDDEN")
    }

    @Test
    @DisplayName("ErrorStatus.CONFLICT 는 연산 요구사항이 우리 서버의 상황과 충돌되는 경우(예:닉네임 중복)를 나타내는 에러 유형이다.")
    fun testConflict() {
        val conflict = ErrorStatus.CONFLICT
        assertThat(conflict.name).isEqualTo("CONFLICT")
    }

    @Test
    @DisplayName("ErrorStatus.APPLICATION_ERROR 는 우리 서버 자체 문제임을 나타내는 에러 유형이다.")
    fun testApplicationError() {
        val applicationError = ErrorStatus.APPLICATION_ERROR
        assertThat(applicationError.name).isEqualTo("APPLICATION_ERROR")
    }

}