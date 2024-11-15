package com.ttasjwi.board.system.core.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZoneId

@DisplayName("TimeRule: 우리 서비스의 시간대 규칙")
class TimeRuleTest {

    @Test
    @DisplayName("우리 서비스의 기준 시간대는 서울 시간을 기준으로 삼는다.")
    fun testZoneId() {
        assertThat(TimeRule.ZONE_ID).isEqualTo(ZoneId.of("Asia/Seoul"))
    }
}
