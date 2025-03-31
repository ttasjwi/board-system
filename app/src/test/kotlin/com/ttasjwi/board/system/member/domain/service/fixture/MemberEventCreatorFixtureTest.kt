package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.fixture.memberFixtureRegistered
import com.ttasjwi.board.system.member.domain.service.MemberEventCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("MemberEventCreatorFixture 테스트")
class MemberEventCreatorFixtureTest {

    private lateinit var memberEventCreator: MemberEventCreator

    @BeforeEach
    fun setup() {
        memberEventCreator = MemberEventCreatorFixture()
    }

    @Nested
    @DisplayName("onMemberRegistered: 회원 가입했을 때, 회원가입됨 이벤트 생성")
    inner class OnMemberRegistered {

        @Test
        @DisplayName("onMemberRegistered: 회원 가입됨 이벤트를 생성한다")
        fun test() {
            // given
            val member = memberFixtureRegistered()

            // when
            val event = memberEventCreator.onMemberRegistered(member)
            val data = event.data

            // then
            assertThat(event.occurredAt).isEqualTo(member.registeredAt)
            assertThat(data.memberId).isEqualTo(member.id!!.value)
            assertThat(data.email).isEqualTo(member.email.value)
            assertThat(data.username).isEqualTo(member.username.value)
            assertThat(data.nickname).isEqualTo(member.nickname.value)
            assertThat(data.roleName).isEqualTo(member.role.name)
            assertThat(data.registeredAt).isEqualTo(member.registeredAt)
        }
    }
}
