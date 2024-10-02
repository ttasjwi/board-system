package com.ttasjwi.board.system.core.exception

/**
 * 예외가 어떤 이유로 발생했는 지 설명하기 위한 상태 유형입니다.
 */
enum class ErrorStatus {

    /**
     * 연산의 대상을 찾지 못 한 경우
     */
    NOT_FOUND,

    /**
     * 미인증, 인증 실패, ...
     */
    UNAUTHENTICATED,

    /**
     * 요청사항이 애플리케이션 연산이 정의한 입력값 조건과 일치하지 않을 때
     */
    INVALID_ARGUMENT,

    /**
     * 연산을 수행할 권한이 없을 때
     */
    FORBIDDEN,

    /**
     * 요구사항이 현재 애플리케이션 상태와 충돌할 때
     * (예: 닉네임 중복, ...)
     */
    CONFLICT,


    /**
     * 애플리케이션 자체적인 문제 혹은 다른 애플리케이션 연동 과정(DB 장애, 타사 API 장애, 개발자 실수로 인해 처리하지 못한 예외...)
     */
    APPLICATION_ERROR
}