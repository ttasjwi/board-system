package com.ttasjwi.board.system.global.time.impl

import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.global.time.impl.TimeManagerImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TimeManagerImpl 테스트")
class TimeManagerImplTest {

    private lateinit var timeManager: TimeManagerImpl

    companion object {
        private val log = getLogger(TimeManagerImplTest::class.java)
    }

    @BeforeEach
    fun setup() {
        timeManager = TimeManagerImpl()
    }

    @Test
    @DisplayName("작동하는 지 테스트")
    fun test() {
        val currentTime = timeManager.now()
        log.info { "currentTime: $currentTime" }
        assertThat(currentTime).isNotNull()
    }
}
