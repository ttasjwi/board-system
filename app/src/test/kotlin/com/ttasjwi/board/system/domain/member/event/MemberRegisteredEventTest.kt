package com.ttasjwi.board.system.domain.member.event

import com.ttasjwi.board.system.global.auth.Role
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MemberRegisteredEvent 테스트")
class MemberRegisteredEventTest {

    @Test
    @DisplayName("회원이 가입됨을 나타내는 이벤트로, 이벤트 구독자들에게 필요한 최소한의 프로퍼티만을 가져야한다.")
    fun test() {
        val registeredAt = appDateTimeFixture(year = 2024)
        val memberId = 4L
        val email = "bye@gmail.com"
        val username = "ttasjwi"
        val role = Role.USER
        val nickname = "땃쥐"

        val event = memberRegisteredEventFixture(
            registeredAt = registeredAt,
            memberId = memberId,
            email = email,
            username = username,
            role = role,
            nickname = nickname,
        )
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(registeredAt)
        assertThat(data.memberId).isEqualTo(memberId)
        assertThat(data.email).isEqualTo(email)
        assertThat(data.username).isEqualTo(username)
        assertThat(data.nickname).isEqualTo(nickname)
        assertThat(data.roleName).isEqualTo(role.name)
        assertThat(data.registeredAt).isEqualTo(registeredAt)
    }
}
