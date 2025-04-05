package com.ttasjwi.board.system.global.time.fixture

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TimeFixture 테스트")
class TimeFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본 값을 가진다.")
    fun test() {
        val time = appDateTimeFixture()
        assertThat(time).isInstanceOf(AppDateTime::class.java)
        assertThat(time.year).isNotNull()
        assertThat(time.month).isNotNull()
        assertThat(time.dayOfMonth).isNotNull()
        assertThat(time.hour).isNotNull()
        assertThat(time.minute).isNotNull()
        assertThat(time.second).isNotNull()
        assertThat(time.zone).isNotNull()
    }

    @Test
    @DisplayName("인자를 커스텀하게 지정해서 생성할 수 있다.")
    fun test2() {
        val year = 2024
        val month = 2
        val dayOfMonth = 1
        val hour = 4
        val minute = 6
        val second = 7
        val time = appDateTimeFixture(
            year = year,
            month = month,
            dayOfMonth = dayOfMonth,
            hour = hour,
            minute = minute,
            second = second,
        )

        assertThat(time).isInstanceOf(AppDateTime::class.java)
        assertThat(time.year).isEqualTo(year)
        assertThat(time.month).isEqualTo(month)
        assertThat(time.dayOfMonth).isEqualTo(dayOfMonth)
        assertThat(time.hour).isEqualTo(hour)
        assertThat(time.minute).isEqualTo(minute)
        assertThat(time.second).isEqualTo(second)
        assertThat(time.zone).isEqualTo(AppDateTime.SERVER_ZONE)
    }
}
