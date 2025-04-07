package com.ttasjwi.board.system.domain.member.event

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MemberRegisteredEvent Fixture 테스트")
class MemberRegisteredEventFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도, 기본값이 존재한다.")
    fun test1() {
        val event = memberRegisteredEventFixture()
        val data = event.data

        assertThat(event.occurredAt).isNotNull()
        assertThat(data.memberId).isNotNull()
        assertThat(data.email).isNotNull()
        assertThat(data.username).isNotNull()
        assertThat(data.nickname).isNotNull()
        assertThat(data.registeredAt).isNotNull()
    }

    @Test
    @DisplayName("값을 지정하여 생성하면 그 값을 가지고 있다.")
    fun test2() {
        val registeredAt = appDateTimeFixture(year = 2024)
        val memberId = 4L
        val email = "bye@gmail.com"
        val username = "ttasjwi"
        val nickname = "땃쥐"

        val event = memberRegisteredEventFixture(
            registeredAt = registeredAt,
            memberId = memberId,
            email = email,
            username = username,
            nickname = nickname,
        )
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(registeredAt)
        assertThat(data.memberId).isEqualTo(memberId)
        assertThat(data.email).isEqualTo(email)
        assertThat(data.username).isEqualTo(username)
        assertThat(data.nickname).isEqualTo(nickname)
        assertThat(data.registeredAt).isEqualTo(registeredAt)
    }

}
