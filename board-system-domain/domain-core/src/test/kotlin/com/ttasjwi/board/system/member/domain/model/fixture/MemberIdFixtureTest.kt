package com.ttasjwi.board.system.member.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("memberIdFixture() : MemberId의 픽스쳐 생성")
class MemberIdFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도, 기본값 value 를 가지고 있다.")
    fun test1() {
        val testMemberId = memberIdFixture()
        assertThat(testMemberId.value).isNotNull()
    }

    @Test
    @DisplayName("호출시 전달한 아이디 값을 value 로 갖고 있다.")
    fun test2() {
        val value = 2L
        val testMemberId = memberIdFixture(value)

        assertThat(testMemberId.value).isEqualTo(value)
    }
}
