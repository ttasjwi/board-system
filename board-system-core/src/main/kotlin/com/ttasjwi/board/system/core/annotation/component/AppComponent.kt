package com.ttasjwi.board.system.core.annotation.component

/**
 * 애플리케이션에서 싱글톤으로 관리하는 컴포넌트임을 나타내는 마커 어노테이션입니다.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class AppComponent
