package com.ttasjwi.board.system.core.domain.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.time.ZonedDateTime

@DisplayName("DomainEvent 테스트")
class DomainEventTest {

    private class TestDomainEvent(
        occurredAt: ZonedDateTime,
        name: String,
        age: Int,
    ) : DomainEvent<TestDomainEvent.TestData>(occurredAt, TestData(name, age)) {

        class TestData(
            val name: String,
            val age: Int,
        )
    }

    @Test
    @DisplayName("개발자는 커스텀한 데이터를 이벤트 객체에 담을 수 있고 이를 꺼내서 쓸 수 있다.")
    fun testData() {
        val occurredAt =ZonedDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneId.of("Asia/Seoul"))
        val event = TestDomainEvent(
            occurredAt,
            "ttasjwi",
            20
        )

        val data = event.data

        assertThat(data.name).isEqualTo("ttasjwi")
        assertThat(data.age).isEqualTo(20)
    }


    @Test
    @DisplayName("이벤트는 발생한 시간 정보를 가진다.")
    fun testTime() {
        val occurredAt = ZonedDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneId.of("Asia/Seoul"))
        val event = TestDomainEvent(
            occurredAt,
            "ttasjwi",
            20
        )

        assertThat(event.occurredAt).isEqualTo(occurredAt)
    }

}
