package com.ttasjwi.board.system.user.domain.model.fixture

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UserFixture 테스트")
class UserFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가지며, id는 null 이 아니다.")
    fun test1() {
        val user = userFixture()

        assertThat(user.userId).isNotNull
        assertThat(user.email).isNotNull
        assertThat(user.password).isNotNull
        assertThat(user.username).isNotNull
        assertThat(user.nickname).isNotNull
        assertThat(user.role).isNotNull
        assertThat(user.registeredAt).isNotNull
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val userId = 1L
        val email = "test@gmail.com"
        val password = "1234"
        val username = "test"
        val nickname = "kyaru"
        val role = Role.USER
        val registeredAt = appDateTimeFixture()
        val user = userFixture(
            userId = userId,
            email = email,
            password = password,
            username = username,
            nickname = nickname,
            role = role,
            registeredAt = registeredAt,
        )

        assertThat(user.userId).isEqualTo(userId)
        assertThat(user.email).isEqualTo(email)
        assertThat(user.password).isEqualTo(password)
        assertThat(user.username).isEqualTo(username)
        assertThat(user.nickname).isEqualTo(nickname)
        assertThat(user.role).isEqualTo(role)
        assertThat(user.registeredAt).isEqualTo(registeredAt)
    }
}
