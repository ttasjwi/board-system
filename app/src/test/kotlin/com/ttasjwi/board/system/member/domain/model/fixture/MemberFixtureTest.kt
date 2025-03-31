package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("MemberFixture 테스트")
class MemberFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가지며, id는 null 이 아니다.")
    fun test1() {
        val member = memberFixture()

        assertThat(member.id).isNotNull
        assertThat(member.email).isNotNull
        assertThat(member.password).isNotNull
        assertThat(member.username).isNotNull
        assertThat(member.nickname).isNotNull
        assertThat(member.role).isNotNull
        assertThat(member.registeredAt).isNotNull
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val id = 1L
        val email = "test@gmail.com"
        val password = "1234"
        val username = "test"
        val nickname = "kyaru"
        val role = Role.USER
        val registeredAt = timeFixture()
        val member = memberFixture(
            id = id,
            email = email,
            password = password,
            username = username,
            nickname = nickname,
            role = role,
            registeredAt = registeredAt,
        )

        assertThat(member.id).isEqualTo(id)
        assertThat(member.email).isEqualTo(emailFixture(email))
        assertThat(member.password.value).isEqualTo(encodedPasswordFixture(password).value)
        assertThat(member.username).isEqualTo(usernameFixture(username))
        assertThat(member.nickname).isEqualTo(nicknameFixture(nickname))
        assertThat(member.role).isEqualTo(role)
        assertThat(member.registeredAt).isEqualTo(registeredAt)
    }
}
