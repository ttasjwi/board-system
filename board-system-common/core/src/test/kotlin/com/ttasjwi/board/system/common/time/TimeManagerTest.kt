package com.ttasjwi.board.system.common.time

import com.ttasjwi.board.system.common.logger.getLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TimeManager : 현재 시간을 반환하는 역할")
class TimeManagerTest {

    private val log = getLogger(TimeManagerTest::class.java)

    @Test
    @DisplayName("TimeManager.default() 를 통해 기본 TimeManager 를 획득할 수 있다.")
    fun factoryTest() {
        val timeManager = TimeManager.default()
        val currentTime = timeManager.now()

        log.info { "currentTime: $currentTime" }

        assertThat(timeManager).isInstanceOf(DefaultTimeManager::class.java)
        assertThat(currentTime).isNotNull()
    }
}
