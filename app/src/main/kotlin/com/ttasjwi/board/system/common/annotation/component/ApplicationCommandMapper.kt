package com.ttasjwi.board.system.common.annotation.component

/**
 * 애플리케이션 명령 매퍼(요청을 애플리케이션 명령으로 변환)를 나타내는 마커 어노테이션입니다.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@AppComponent
annotation class ApplicationCommandMapper
