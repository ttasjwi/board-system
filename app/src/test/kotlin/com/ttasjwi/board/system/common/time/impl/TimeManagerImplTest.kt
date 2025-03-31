package com.ttasjwi.board.system.common.time.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TimeManagerImpl 테스트")
class TimeManagerImplTest {

    private lateinit var timeManager: TimeManagerImpl

    @BeforeEach
    fun setup() {
        timeManager = TimeManagerImpl()
    }

    /**
     * TimeManagerImpl 내부구현은 사실상 Java API ZonedDateTime.now() 를 사용한 것인데
     * 이것을 테스트하는 것은 Java API 에 대한 신뢰성을 테스트하는 별 의미없는 행위가 되므로
     * 형식상의 테스트만 작성함
     */
    @Test
    @DisplayName("작동하는 지 테스트")
    fun test() {
        val currentTime = timeManager.now()
        assertThat(currentTime).isNotNull()
    }
}
