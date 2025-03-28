package com.ttasjwi.board.system.common.time.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TimeManagerFixture 테스트")
class TimeManagerFixtureTest {

    private lateinit var timeManagerFixture: TimeManagerFixture

    @Test
    @DisplayName("생성자 디폴트 파라미터 테스트")
    fun defaultTest() {
        timeManagerFixture = TimeManagerFixture()

        val currentTime = timeManagerFixture.now()
        assertThat(currentTime).isEqualTo(timeFixture())
    }

    @Test
    @DisplayName("생성자로 시간 지정했을 때 테스트")
    fun constructorTest() {
        timeManagerFixture = TimeManagerFixture(timeFixture(minute = 3))
        val currentTime = timeManagerFixture.now()
        assertThat(currentTime).isEqualTo(timeFixture(minute = 3))
    }

    @Test
    @DisplayName("changeCurrentTime 으로 시간 변경 후 테스트")
    fun changeTest() {
        timeManagerFixture = TimeManagerFixture()

        timeManagerFixture.changeCurrentTime(timeFixture(minute=5))
        val currentTime = timeManagerFixture.now()
        assertThat(currentTime).isEqualTo(timeFixture(minute=5))
    }
}
