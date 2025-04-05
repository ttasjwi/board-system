package com.ttasjwi.board.system.global.exception


/**
 * 애플리케이션 내에서 발생하는 커스텀 예외의 기본 클래스.
 * 이 클래스를 상속하여 각 예외에 맞는 커스텀 예외를 정의할 수 있습니다.
 *
 * @param status 예외가 발생한 이유를 설명하는 상태 정보.
 * @param code 예외 메시지 구성을 위한 코드 ("Error.xxx" 형식).
 * @param args 메시지 템플릿에서 사용할 인자들 (빈 리스트일 경우 인자 없고, 순서대로 사용됨).
 * @param source 예외를 발생시킨 필드 또는 맥락을 설명하는 값 (예: "nickname")
 * @param debugMessage 디버깅을 위한 메시지 (사용자에게 제공되지 않음).
 * @param cause 근본 원인이 되는 예외 (선택적).
 *
 *
 * @author ttasjwi
 * @date 2024/10/02
 */
abstract class CustomException(
    val status: ErrorStatus,
    val code: String,
    val args: List<Any?>,
    val source: String,
    val debugMessage: String,
    cause: Throwable? = null
) : RuntimeException(debugMessage, cause)
