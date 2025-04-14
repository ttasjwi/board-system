package com.ttasjwi.board.system.common.time

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class AppDateTime private constructor(zonedDateTime: ZonedDateTime) : Comparable<AppDateTime> {

    /**
     * 기준 시간은 java.time.ZonedDateTime 으로 설정.
     * 나노초 단위로의 시간은 관리하지 않기로 하였다.
     */
    private val zonedDateTime: ZonedDateTime = zonedDateTime.withNano(0)

    companion object {

        /**
         * 우리 서비스의 기준 시간대. 서울 표준시이다.
         */
        val SERVER_ZONE: ZoneId = ZoneId.of("Asia/Seoul")

        /**
         * 연월일 시분초로 시간을 생성. 시간대 정보는 우리 서비스의 SERVER_ZONE 을 기준으로 생성된다.
         */
        fun of(
            year: Int,
            month: Int,
            dayOfMonth: Int,
            hour: Int,
            minute: Int,
            second: Int,
        ): AppDateTime {
            return AppDateTime(ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second, 0, SERVER_ZONE))
        }

        /**
         * 현재 시간을 생성. 시간대 정보는 우리 서비스의 SERVER_ZONE 을 기준으로 생성된다.
         */
        fun now(): AppDateTime {
            return AppDateTime(ZonedDateTime.now(SERVER_ZONE))
        }

        /**
         * LocalDateTime 으로부터 생성. 우리 서비스의 SERVER_ZONE 을 기준으로 생성된다.
         */
        fun from(localDate: LocalDateTime): AppDateTime {
            return AppDateTime(ZonedDateTime.of(localDate, SERVER_ZONE))
        }

        /**
         * ZonedDateTime 으로부터 생성.
         * 여기서 전달되는 ZonedDateTime 은 우리 서비스의 SERVER_ZONE 이 적용된 채 생성됨이 전제되므로 굳이 Zone을 변경하진 않는다.
         */
        fun from(zonedDateTime: ZonedDateTime): AppDateTime {
            require(zonedDateTime.zone == SERVER_ZONE) {
                "생성시 전달된 객체의 시간대가 우리 서비스의 Zone 이 아님. (우리 서비스의 Zone=$SERVER_ZONE, 전달된 Zone=${zonedDateTime.zone})"
            }
            return AppDateTime(zonedDateTime)
        }

        /**
         * Instant 로부터 생성. 우리 서비스의 SERVER_ZONE 을 기준으로 생성된다.
         */
        fun from(instant: Instant): AppDateTime {
            return AppDateTime(ZonedDateTime.ofInstant(instant, SERVER_ZONE))
        }
    }

    /**
     * 연 정보를 반환한다.
     */
    val year: Int
        get() = zonedDateTime.year

    /**
     * 월 정보를 반환한다.
     */
    val month: Int
        get() = zonedDateTime.monthValue

    /**
     * 일 정보를 반환한다.
     */
    val dayOfMonth: Int
        get() = zonedDateTime.dayOfMonth

    /**
     * 시 정보를 반환한다.
     */
    val hour: Int
        get() = zonedDateTime.hour

    /**
     * 분 정보를 반환한다.
     */
    val minute: Int
        get() = zonedDateTime.minute

    /**
     * 초 정보를 반환한다.
     */
    val second: Int
        get() = zonedDateTime.second

    /**
     * 시간대 정보를 반환한다.
     */
    val zone: ZoneId
        get() = zonedDateTime.zone

    /**
     * 시간대 정보이 없는 LocalDateTime 으로 변환합니다.
     */
    fun toLocalDateTime(): LocalDateTime = zonedDateTime.toLocalDateTime()

    /**
     * 시간대 정보를 포함한 ZonedDateTime 으로 변환합니다.
     */
    fun toZonedDateTime(): ZonedDateTime = zonedDateTime

    /**
     * 절대 시간을 나타내는 Instant 로 변환합니다.
     */
    fun toInstant(): Instant = zonedDateTime.toInstant()

    /**
     * 지정한 시단위 만큼 시간을 증가시킨 AppDateTime 을 반환합니다.
     */
    fun plusHours(hours: Long): AppDateTime = AppDateTime(zonedDateTime.plusHours(hours))

    /**
     * 지정산 시단위 만큼 시간을 감소시킨 AppDateTime 을 반환합니다.
     */
    fun minusHours(hours: Long): AppDateTime = AppDateTime(zonedDateTime.minusHours(hours))

    /**
     * 지정한 분단위 만큼 시간을 증가시킨 AppDateTime 을 반환합니다.
     */
    fun plusMinutes(minutes: Long): AppDateTime = AppDateTime(zonedDateTime.plusMinutes(minutes))

    /**
     * 지정한 분단위만큼 시간을 감소시킨 AppDateTime 을 반환합니다.
     */
    fun minusMinutes(minutes: Long): AppDateTime = AppDateTime(zonedDateTime.minusMinutes(minutes))

    /**
     * 지정한 초단위만큼 시간을 증가시킨 AppDateTime 을 반환합니다.
     */
    fun plusSeconds(seconds: Long): AppDateTime = AppDateTime(zonedDateTime.plusSeconds(seconds))

    /**
     * 지정한 초단위 만큼 시간을 감소시킨 AppDateTime 을 반환합니다.
     */
    fun minusSeconds(seconds: Long): AppDateTime = AppDateTime(zonedDateTime.minusSeconds(seconds))

    /**
     * 동등성 확인
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AppDateTime) return false
        if (zonedDateTime != other.zonedDateTime) return false
        return true
    }

    /**
     * 해시코드
     */
    override fun hashCode(): Int {
        return zonedDateTime.hashCode()
    }

    /**
     * 비교 기준
     */
    override fun compareTo(other: AppDateTime): Int {
        return this.zonedDateTime.compareTo(other.zonedDateTime)
    }

    /**
     * 디버깅 문자열
     */
    override fun toString(): String {
        return zonedDateTime.toString()
    }
}
