package com.ttasjwi.board.system.common.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@DisplayName("AppDateTime: 커스텀 DateTime 클래스")
class AppDateTimeTest {

    @Test
    @DisplayName("SERVER_ZONE 은 Asia/Seoul 의 시간대를 의미한다.")
    fun serverZoneTest() {
        assertThat(AppDateTime.SERVER_ZONE).isEqualTo(ZoneId.of("Asia/Seoul"))
    }

    @Test
    @DisplayName("나노초 단위의 시간 정보는 말소된다.")
    fun nanoSecondZeroTest() {
        // given
        val zonedDateTime = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 133, AppDateTime.SERVER_ZONE)

        // when
        val appDateTime = AppDateTime.from(zonedDateTime)
        val appDateTimeZonedDateTime = appDateTime.toZonedDateTime()

        // then
        assertThat(appDateTimeZonedDateTime.nano).isNotEqualTo(zonedDateTime.nano)
        assertThat(appDateTimeZonedDateTime.nano).isEqualTo(0)
    }


    @Test
    @DisplayName("now() 는 현재 시간을 생성하며, 시간대는 SERVER_ZONE 을 따른다.")
    fun nowTest() {
        val now = AppDateTime.now()
        assertThat(now).isNotNull()
        assertThat(now.zone).isEqualTo(AppDateTime.SERVER_ZONE)
    }

    @Test
    @DisplayName("of() : 연,월,일,시,분,초을 커스텀하게 설정하여 생성할 수 있다.")
    fun ofTest() {
        // given
        val year = 2024
        val month = 2
        val dayOfMonth = 1
        val hour = 4
        val minute = 6
        val second = 7

        // when
        val appDateTime = AppDateTime.of(
            year = year,
            month = month,
            dayOfMonth = dayOfMonth,
            hour = hour,
            minute = minute,
            second = second,
        )

        // then
        assertThat(appDateTime).isNotNull()
        assertThat(appDateTime.year).isEqualTo(year)
        assertThat(appDateTime.month).isEqualTo(month)
        assertThat(appDateTime.dayOfMonth).isEqualTo(dayOfMonth)
        assertThat(appDateTime.hour).isEqualTo(hour)
        assertThat(appDateTime.minute).isEqualTo(minute)
        assertThat(appDateTime.second).isEqualTo(second)
        assertThat(appDateTime.zone).isEqualTo(AppDateTime.SERVER_ZONE)
    }

    @Test
    @DisplayName("from() : LocalDateTime 으로부터 생성할 수 있다. 이 때 시간대는 SERVER_ZONE 을 따른다.")
    fun fromLocalDateTimeTest() {
        // given
        val localDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0)

        // when
        val appDateTime = AppDateTime.from(localDateTime)

        // then
        assertThat(appDateTime).isNotNull()
        assertThat(appDateTime.year).isEqualTo(localDateTime.year)
        assertThat(appDateTime.month).isEqualTo(localDateTime.month.value)
        assertThat(appDateTime.dayOfMonth).isEqualTo(localDateTime.dayOfMonth)
        assertThat(appDateTime.hour).isEqualTo(localDateTime.hour)
        assertThat(appDateTime.minute).isEqualTo(localDateTime.minute)
        assertThat(appDateTime.second).isEqualTo(localDateTime.second)
        assertThat(appDateTime.zone).isEqualTo(AppDateTime.SERVER_ZONE)
    }

    @Nested
    @DisplayName("from() : ZonedDateTime 으로부터 생성할 수 있다.")
    inner class FromZonedDateTime {

        @Test
        @DisplayName("시간대가 SERVER_ZONE 이면 정상 생성된다.")
        fun fromZonedDateTimeSuccessTest() {
            // given
            val zonedDateTime = ZonedDateTime.of(
                2025, 1, 1, 0, 0, 0, 0, AppDateTime.SERVER_ZONE
            )
            // when
            val appDateTime = AppDateTime.from(zonedDateTime)

            // then
            assertThat(appDateTime).isNotNull()
            assertThat(appDateTime.year).isEqualTo(zonedDateTime.year)
            assertThat(appDateTime.month).isEqualTo(zonedDateTime.month.value)
            assertThat(appDateTime.dayOfMonth).isEqualTo(zonedDateTime.dayOfMonth)
            assertThat(appDateTime.hour).isEqualTo(zonedDateTime.hour)
            assertThat(appDateTime.minute).isEqualTo(zonedDateTime.minute)
            assertThat(appDateTime.second).isEqualTo(zonedDateTime.second)
            assertThat(appDateTime.zone).isEqualTo(AppDateTime.SERVER_ZONE)
        }

        @Test
        @DisplayName("시간대가 SERVER_ZONE 가 아니면 예외가 발생한다.(서버 에러. 정상적인 상황에서 발생할 수 없는 서버 에러)")
        fun fromZonedDateTimeFailureTest() {
            // given
            val zonedDateTime = ZonedDateTime.of(
                2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")
            )
            // when
            val ex = assertThrows<IllegalArgumentException> { AppDateTime.from(zonedDateTime) }

            // then
            assertThat(ex.message).isEqualTo(
                "생성시 전달된 객체의 시간대가 우리 서비스의 Zone 이 아님. (우리 서비스의 Zone=${AppDateTime.SERVER_ZONE}, 전달된 Zone=${zonedDateTime.zone})"
            )
        }
    }


    @Test
    @DisplayName("from(): Instant 로부터 생성할 수 있다. 이 때 시간대는 SERVER_ZONE 을 따른다.")
    fun fromInstantTest() {
        // given
        val instant = Instant.ofEpochMilli(12234554758)

        // when
        val appDateTime = AppDateTime.from(instant)

        // then
        assertThat(appDateTime).isNotNull()
        assertThat(appDateTime.zone).isEqualTo(AppDateTime.SERVER_ZONE)
    }


    @Test
    @DisplayName("toLocalDateTime() : 시간대 정보를 제거한 LocalDateTime 반환")
    fun toLocalDateTimeTest() {
        // given
        val zonedDateTime = ZonedDateTime.of(2025, 1, 1, 0,0,0, 1234, AppDateTime.SERVER_ZONE)
        val appDateTime = AppDateTime.from(zonedDateTime)

        // when
        val localDateTime = appDateTime.toLocalDateTime()

        // then
        assertThat(localDateTime).isEqualTo(zonedDateTime.toLocalDateTime().withNano(0))
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2025,1,1,0,0,0))
        assertThat(AppDateTime.from(localDateTime)).isEqualTo(appDateTime)
    }

    @Test
    @DisplayName("toZonedDateTime() : 시간대 정보를 포함한 ZonedDateTime 반환")
    fun toZonedDateTimeTest() {
        // given
        val localDateTime = LocalDateTime.of(2025, 1, 1, 0,0,0, 1234)
        val appDateTime = AppDateTime.from(localDateTime)

        // when
        val zonedDateTime = appDateTime.toZonedDateTime()

        // then
        assertThat(zonedDateTime).isEqualTo(localDateTime.atZone(AppDateTime.SERVER_ZONE).withNano(0))
        assertThat(zonedDateTime.zone).isEqualTo(AppDateTime.SERVER_ZONE)
        assertThat(AppDateTime.from(zonedDateTime)).isEqualTo(appDateTime)
    }


    @Test
    @DisplayName("toInstant(): 절대시간을 의미하는 Instant 객체 반환")
    fun toInstantTest() {
        // given
        val zonedDateTime = ZonedDateTime.of(2025, 1, 1, 0,0,0, 1234, AppDateTime.SERVER_ZONE)
        val appDateTime = AppDateTime.from(zonedDateTime)

        // when
        val instant = appDateTime.toInstant()

        // then
        assertThat(instant).isEqualTo(zonedDateTime.withNano(0).toInstant())
        assertThat(AppDateTime.from(instant)).isEqualTo(appDateTime)
    }


    @Test
    @DisplayName("plusHours() : 지정한 시단위 만큼 시간을 증가시킨 AppDateTime 을 반환한다")
    fun plusHoursTest() {
        // given
        val from1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
        val from2 = AppDateTime.of(2025, 1, 1, 23, 0, 0)
        val from3 = AppDateTime.of(2025, 12, 31, 23, 0, 0)

        // when
        val to1 = from1.plusHours(1)
        val to2 = from2.plusHours(1)
        val to3 = from3.plusHours(3)

        // then
        assertThat(to1).isEqualTo(AppDateTime.of(2025, 1, 1, 1, 0, 0))
        assertThat(to2).isEqualTo(AppDateTime.of(2025, 1, 2, 0, 0, 0))
        assertThat(to3).isEqualTo(AppDateTime.of(2026, 1, 1, 2, 0, 0))
    }


    @Test
    @DisplayName("minusHours() : 지정한 시단위 만큼 시간을 감소시킨 AppDateTime 을 반환한다")
    fun minusHoursTest() {
        // given
        val from1 = AppDateTime.of(2025, 1, 1, 1, 0, 0)
        val from2 = AppDateTime.of(2025, 1, 2, 0, 0, 0)
        val from3 = AppDateTime.of(2026, 1, 1, 3, 0, 0)

        // when
        val to1 = from1.minusHours(1)
        val to2 = from2.minusHours(1)
        val to3 = from3.minusHours(5)

        // then
        assertThat(to1).isEqualTo(AppDateTime.of(2025, 1, 1, 0, 0, 0))
        assertThat(to2).isEqualTo(AppDateTime.of(2025, 1, 1, 23, 0, 0))
        assertThat(to3).isEqualTo(AppDateTime.of(2025, 12, 31, 22, 0, 0))
    }


    @Test
    @DisplayName("plusMinutes() : 지정한 분단위 만큼 시간을 증가시킨 AppDateTime 을 반환한다")
    fun plusMinutesTest() {
        // given
        val from1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
        val from2 = AppDateTime.of(2025, 1, 1, 0, 57, 0)
        val from3 = AppDateTime.of(2025, 12, 31, 23, 59, 0)

        // when
        val to1 = from1.plusMinutes(1)
        val to2 = from2.plusMinutes(5)
        val to3 = from3.plusMinutes(14)

        // then
        assertThat(to1).isEqualTo(AppDateTime.of(2025, 1, 1, 0, 1, 0))
        assertThat(to2).isEqualTo(AppDateTime.of(2025, 1, 1, 1, 2, 0))
        assertThat(to3).isEqualTo(AppDateTime.of(2026, 1, 1, 0, 13, 0))
    }


    @Test
    @DisplayName("minusMinutes() : 지정한 분단위 만큼 시간을 감소시킨 AppDateTime 을 반환한다")
    fun minusMinutesTest() {
        // given
        val from1 = AppDateTime.of(2025, 1, 1, 1, 3, 0)
        val from2 = AppDateTime.of(2025, 1, 1, 1, 23, 0)
        val from3 = AppDateTime.of(2026, 1, 1, 0, 15, 0)

        // when
        val to1 = from1.minusMinutes(3)
        val to2 = from2.minusMinutes(28)
        val to3 = from3.minusMinutes(30)

        // then
        assertThat(to1).isEqualTo(AppDateTime.of(2025, 1, 1, 1, 0, 0))
        assertThat(to2).isEqualTo(AppDateTime.of(2025, 1, 1, 0, 55, 0))
        assertThat(to3).isEqualTo(AppDateTime.of(2025, 12, 31, 23, 45, 0))
    }


    @Test
    @DisplayName("plusSeconds() : 지정한 초단위 만큼 시간을 증가시킨 AppDateTime 을 반환한다")
    fun plusSecondsTest() {
        // given
        val from1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
        val from2 = AppDateTime.of(2025, 1, 1, 0, 1, 47)
        val from3 = AppDateTime.of(2025, 12, 31, 23, 59, 55)

        // when
        val to1 = from1.plusSeconds(1)
        val to2 = from2.plusSeconds(20)
        val to3 = from3.plusSeconds(68)

        // then
        assertThat(to1).isEqualTo(AppDateTime.of(2025, 1, 1, 0, 0, 1))
        assertThat(to2).isEqualTo(AppDateTime.of(2025, 1, 1, 0, 2, 7))
        assertThat(to3).isEqualTo(AppDateTime.of(2026, 1, 1, 0, 1, 3))
    }


    @Test
    @DisplayName("minusSeconds() : 지정한 초단위 만큼 시간을 감소시킨 AppDateTime 을 반환한다")
    fun minusSecondsTest() {
        // given
        val from1 = AppDateTime.of(2025, 1, 1, 0, 0, 3)
        val from2 = AppDateTime.of(2025, 1, 1, 0, 1, 23)
        val from3 = AppDateTime.of(2026, 1, 1, 0, 0, 15)

        // when
        val to1 = from1.minusSeconds(3)
        val to2 = from2.minusSeconds(28)
        val to3 = from3.minusSeconds(45)

        // then
        assertThat(to1).isEqualTo(AppDateTime.of(2025, 1, 1, 0, 0, 0))
        assertThat(to2).isEqualTo(AppDateTime.of(2025, 1, 1, 0, 0, 55))
        assertThat(to3).isEqualTo(AppDateTime.of(2025, 12, 31, 23, 59, 30))
    }

    @Nested
    @DisplayName("Equals & HashCode")
    inner class EqualsAndHashCodeTest {

        @Test
        @DisplayName("값이 같으면 동등하다")
        fun testEquals() {
            // given
            val appDateTime = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val other = AppDateTime.of(2025, 1, 1, 0, 0, 0)

            // when
            val equals = appDateTime.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(appDateTime.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("참조가 같으면 동등하다")
        fun testSameReference() {
            // given
            val appDateTime = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val other = appDateTime

            // when
            val equals = appDateTime.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(appDateTime.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("AppDateTime 이 아니면 동등하지 않다")
        fun testDifferentType() {
            // given
            val appDateTime = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val other = 1L

            // when
            val equals = appDateTime.equals(other)

            // then
            assertThat(equals).isFalse()
        }


        @Test
        @DisplayName("비교대상이 null 이면 동등하지 않다")
        fun testNull() {
            // given
            val appDateTime = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val other = null

            // when
            val equals = appDateTime.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("다른 ZonedDateTime 을 기반으로 생성되면 동등하지 않다")
        fun testDifferentAuthMember() {
            // given
            val appDateTime = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val other = AppDateTime.of(2025, 2, 1, 0, 0, 0)

            // when
            val equals = appDateTime.equals(other)

            // then
            assertThat(equals).isFalse()
        }

    }

    @Nested
    @DisplayName("대소관계 테스트")
    inner class CompareToTest {

        @Test
        @DisplayName("연이 작으면 음수, 같으면 0, 크면 양수")
        fun testYear() {
            // given
            val dateTime1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime2 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime3 = AppDateTime.of(2026, 1, 1, 0, 0, 0)

            // when
            assertThat(dateTime1.compareTo(dateTime2)).isEqualTo(0)
            assertThat(dateTime1.compareTo(dateTime3)).isLessThan(0)
            assertThat(dateTime3.compareTo(dateTime1)).isGreaterThan(0)
            assertThat(dateTime1 == dateTime2).isTrue()
            assertThat(dateTime1 < dateTime3).isTrue()
            assertThat(dateTime3 > dateTime1).isTrue()
        }

        @Test
        @DisplayName("월이 작으면 음수, 같으면 0, 크면 양수")
        fun testMonth() {
            // given
            val dateTime1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime2 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime3 = AppDateTime.of(2025, 2, 1, 0, 0, 0)

            // when
            assertThat(dateTime1.compareTo(dateTime2)).isEqualTo(0)
            assertThat(dateTime1.compareTo(dateTime3)).isLessThan(0)
            assertThat(dateTime3.compareTo(dateTime1)).isGreaterThan(0)
            assertThat(dateTime1 == dateTime2).isTrue()
            assertThat(dateTime1 < dateTime3).isTrue()
            assertThat(dateTime3 > dateTime1).isTrue()
        }

        @Test
        @DisplayName("일이 작으면 음수, 같으면 0, 크면 양수")
        fun testDayOfMonth() {
            // given
            val dateTime1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime2 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime3 = AppDateTime.of(2025, 1, 2, 0, 0, 0)

            // when
            assertThat(dateTime1.compareTo(dateTime2)).isEqualTo(0)
            assertThat(dateTime1.compareTo(dateTime3)).isLessThan(0)
            assertThat(dateTime3.compareTo(dateTime1)).isGreaterThan(0)
            assertThat(dateTime1 == dateTime2).isTrue()
            assertThat(dateTime1 < dateTime3).isTrue()
            assertThat(dateTime3 > dateTime1).isTrue()
        }

        @Test
        @DisplayName("시간이 작으면 음수, 같으면 0, 크면 양수")
        fun testHours() {
            // given
            val dateTime1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime2 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime3 = AppDateTime.of(2025, 1, 1, 1, 0, 0)

            // when
            assertThat(dateTime1.compareTo(dateTime2)).isEqualTo(0)
            assertThat(dateTime1.compareTo(dateTime3)).isLessThan(0)
            assertThat(dateTime3.compareTo(dateTime1)).isGreaterThan(0)
            assertThat(dateTime1 == dateTime2).isTrue()
            assertThat(dateTime1 < dateTime3).isTrue()
            assertThat(dateTime3 > dateTime1).isTrue()
        }

        @Test
        @DisplayName("분이 작으면 음수, 같으면 0, 크면 양수")
        fun testMinutes() {
            // given
            val dateTime1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime2 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime3 = AppDateTime.of(2025, 1, 1, 0, 1, 0)

            // when
            assertThat(dateTime1.compareTo(dateTime2)).isEqualTo(0)
            assertThat(dateTime1.compareTo(dateTime3)).isLessThan(0)
            assertThat(dateTime3.compareTo(dateTime1)).isGreaterThan(0)
            assertThat(dateTime1 == dateTime2).isTrue()
            assertThat(dateTime1 < dateTime3).isTrue()
            assertThat(dateTime3 > dateTime1).isTrue()
        }

        @Test
        @DisplayName("초가 작으면 음수, 같으면 0, 크면 양수")
        fun testSeconds() {
            // given
            val dateTime1 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime2 = AppDateTime.of(2025, 1, 1, 0, 0, 0)
            val dateTime3 = AppDateTime.of(2025, 1, 1, 0, 0, 1)

            // when
            assertThat(dateTime1.compareTo(dateTime2)).isEqualTo(0)
            assertThat(dateTime1.compareTo(dateTime3)).isLessThan(0)
            assertThat(dateTime3.compareTo(dateTime1)).isGreaterThan(0)
            assertThat(dateTime1 == dateTime2).isTrue()
            assertThat(dateTime1 < dateTime3).isTrue()
            assertThat(dateTime3 > dateTime1).isTrue()
        }

        @Test
        @DisplayName("대소관계는 zonedDateTime 의 흐름을 따른다.")
        fun testZonedDateTime() {
            // given
            val zonedDateTime1 = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, AppDateTime.SERVER_ZONE)
            val zonedDateTime2 = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, AppDateTime.SERVER_ZONE)
            val zonedDateTime3 = ZonedDateTime.of(2025, 1, 1, 0, 0, 1, 0, AppDateTime.SERVER_ZONE)

            val appDateTime1 = AppDateTime.from(zonedDateTime1)
            val appDateTime2 = AppDateTime.from(zonedDateTime2)
            val appDateTime3 = AppDateTime.from(zonedDateTime3)

            // when
            assertThat(appDateTime1.compareTo(appDateTime2)).isEqualTo(zonedDateTime1.compareTo(zonedDateTime2))
            assertThat(appDateTime1.compareTo(appDateTime3)).isEqualTo(zonedDateTime1.compareTo(zonedDateTime3))
            assertThat(appDateTime3.compareTo(appDateTime1)).isEqualTo(zonedDateTime3.compareTo(zonedDateTime1))
            assertThat(appDateTime1 == appDateTime2).isEqualTo(zonedDateTime1 == zonedDateTime2)
            assertThat(appDateTime1 < appDateTime3).isEqualTo(zonedDateTime1 < zonedDateTime3)
            assertThat(appDateTime3 > appDateTime1).isEqualTo(zonedDateTime3 > zonedDateTime1)
        }
    }

    @Test
    @DisplayName("toString() : 디버깅 문자열 반환")
    fun toStringTest() {
        // given
        val zonedDateTime = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, AppDateTime.SERVER_ZONE)
        val appDateTime = AppDateTime.from(zonedDateTime)

        // when
        val string = appDateTime.toString()

        // then
        assertThat(string).isEqualTo(zonedDateTime.toString())
        assertThat(string).isEqualTo("2025-01-01T00:00+09:00[Asia/Seoul]")
    }
}
