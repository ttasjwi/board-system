package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.memberFixture
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
            val member = memberFixture()

            // when
            val event = memberEventCreator.onMemberRegistered(member)
            val data = event.data

            // then
            assertThat(event.occurredAt).isEqualTo(member.registeredAt)
            assertThat(data.memberId).isEqualTo(member.id)
            assertThat(data.email).isEqualTo(member.email)
            assertThat(data.username).isEqualTo(member.username)
            assertThat(data.nickname).isEqualTo(member.nickname)
            assertThat(data.roleName).isEqualTo(member.role.name)
            assertThat(data.registeredAt).isEqualTo(member.registeredAt)
        }
    }
}
