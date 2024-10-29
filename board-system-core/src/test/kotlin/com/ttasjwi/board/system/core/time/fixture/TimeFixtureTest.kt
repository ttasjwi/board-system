package com.ttasjwi.board.system.core.time.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZoneOffset
import java.time.ZonedDateTime

@DisplayName("TimeFixture 테스트")
class TimeFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본 값을 가진다.")
    fun test() {
        val time = timeFixture()
        assertThat(time).isInstanceOf(ZonedDateTime::class.java)
        assertThat(time.year).isNotNull()
        assertThat(time.month.value).isNotNull()
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
        val nanoOfSecond = 13
        val zone = ZoneOffset.UTC
        val time = timeFixture(
            year = year,
            month = month,
            dayOfMonth = dayOfMonth,
            hour = hour,
            minute = minute,
            second = second,
            nanoOfSecond = nanoOfSecond,
            zone = zone
        )

        assertThat(time).isInstanceOf(ZonedDateTime::class.java)
        assertThat(time.year).isEqualTo(year)
        assertThat(time.month.value).isEqualTo(month)
        assertThat(time.dayOfMonth).isEqualTo(dayOfMonth)
        assertThat(time.hour).isEqualTo(hour)
        assertThat(time.minute).isEqualTo(minute)
        assertThat(time.second).isEqualTo(second)
        assertThat(time.nano).isEqualTo(nanoOfSecond)
        assertThat(time.zone).isEqualTo(zone)
    }
}
