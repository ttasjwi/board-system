package com.ttasjwi.board.system.common.annotation.component

/**
 * 애플리케이션 프로세서(애플리케이션 실질적 명령/질의 처리)를 나타내는 마커 어노테이션입니다.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@AppComponent
annotation class ApplicationProcessor
