package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UserCreator: 사용자 생성 도메인 서비스")
class UserCreatorTest {

    private lateinit var userCreator: UserCreator

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        userCreator = container.userCreator
    }

    @Test
    @DisplayName("create: 값들로부터 사용자를 생성한다.")
    fun createTest() {
        // given
        val email = "hello@gmail.com"
        val rawPassword = "1234567"
        val username = "hello"
        val nickname = "jello"
        val currentTime = appDateTimeFixture(minute = 3)

        // when
        val user = userCreator.create(email, rawPassword, username, nickname, currentTime)

        assertThat(user.userId).isNotNull()
        assertThat(user.email).isEqualTo(email)
        assertThat(user.password).isNotNull()
        assertThat(user.username).isEqualTo(username)
        assertThat(user.nickname).isEqualTo(nickname)
        assertThat(user.role).isEqualTo(Role.USER)
        assertThat(user.registeredAt).isEqualTo(currentTime)
    }


    @Test
    @DisplayName("createRandom: 이메일과 생성시간만 전달받고, 나머지는 랜덤으로 생성한다.")
    fun createRandomTest() {
        // given
        val email = "jello@gmail.com"
        val currentTime = appDateTimeFixture(minute = 3)

        // when
        val user = userCreator.createRandom(email, currentTime)

        // then
        assertThat(user.userId).isNotNull()
        assertThat(user.email).isEqualTo(email)
        assertThat(user.password).isNotNull()
        assertThat(user.username).isNotNull()
        assertThat(user.nickname).isNotNull()
        assertThat(user.role).isEqualTo(Role.USER)
        assertThat(user.registeredAt).isEqualTo(currentTime)
    }
}
