package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MemberCreator: 회원 생성 도메인 서비스")
class MemberCreatorTest {

    private lateinit var memberCreator: MemberCreator

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        memberCreator = container.memberCreator
    }

    @Test
    @DisplayName("create: 값들로부터 회원을 생성한다.")
    fun createTest() {
        // given
        val email = "hello@gmail.com"
        val rawPassword = "1234567"
        val username = "hello"
        val nickname = "jello"
        val currentTime = appDateTimeFixture(minute = 3)

        // when
        val member = memberCreator.create(email, rawPassword, username, nickname, currentTime)

        assertThat(member.memberId).isNotNull()
        assertThat(member.email).isEqualTo(email)
        assertThat(member.password).isNotNull()
        assertThat(member.username).isEqualTo(username)
        assertThat(member.nickname).isEqualTo(nickname)
        assertThat(member.role).isEqualTo(Role.USER)
        assertThat(member.registeredAt).isEqualTo(currentTime)
    }


    @Test
    @DisplayName("createRandom: 이메일과 생성시간만 전달받고, 나머지는 랜덤으로 생성한다.")
    fun createRandomTest() {
        // given
        val email = "jello@gmail.com"
        val currentTime = appDateTimeFixture(minute = 3)

        // when
        val member = memberCreator.createRandom(email, currentTime)

        // then
        assertThat(member.memberId).isNotNull()
        assertThat(member.email).isEqualTo(email)
        assertThat(member.password).isNotNull()
        assertThat(member.username).isNotNull()
        assertThat(member.nickname).isNotNull()
        assertThat(member.role).isEqualTo(Role.USER)
        assertThat(member.registeredAt).isEqualTo(currentTime)
    }
}
