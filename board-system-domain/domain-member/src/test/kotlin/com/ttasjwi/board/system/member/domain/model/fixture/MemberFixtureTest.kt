package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("MemberFixture 테스트")
class MemberFixtureTest {

    @Nested
    @DisplayName("memberFixtureNotRegistered: 가입하지 않은 회원을 테스트용으로 생성")
    inner class NotRegistered {

        @Test
        @DisplayName("인자 없이 생성해도 기본값을 가지며, id는 null 이다.")
        fun test1() {
            val member = memberFixtureNotRegistered()

            assertThat(member.id).isNull()
            assertThat(member.email).isNotNull
            assertThat(member.password).isNotNull
            assertThat(member.username).isNotNull
            assertThat(member.nickname).isNotNull
            assertThat(member.role).isNotNull
            assertThat(member.registeredAt).isNotNull
        }

        @Test
        @DisplayName("커스텀하게 인자를 지정할 수 있으며, 이 때 id는 null 이다.")
        fun test2() {
            val email = "test@gmail.com"
            val password = "1234"
            val username = "test"
            val nickname = "kyaru"
            val role = Role.USER
            val registeredAt = timeFixture()
            val member = memberFixtureNotRegistered(
                email = email,
                password = password,
                username = username,
                nickname = nickname,
                role = role,
                registeredAt = registeredAt,
            )

            assertThat(member.id).isNull()
            assertThat(member.email).isEqualTo(emailFixture(email))
            assertThat(member.password.value).isEqualTo(encodedPasswordFixture(password).value)
            assertThat(member.username).isEqualTo(usernameFixture(username))
            assertThat(member.nickname).isEqualTo(nicknameFixture(nickname))
            assertThat(member.role).isEqualTo(role)
            assertThat(member.registeredAt).isEqualTo(registeredAt)
        }
    }


    @Nested
    @DisplayName("memberFixtureRegistered: 가입한 회원을 테스트용으로 생성")
    inner class Registered {

        @Test
        @DisplayName("인자 없이 생성해도 기본값을 가지며, id는 null 이 아니다.")
        fun test1() {
            val member = memberFixtureRegistered()

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
            val member = memberFixtureRegistered(
                id = id,
                email = email,
                password = password,
                username = username,
                nickname = nickname,
                role = role,
                registeredAt = registeredAt,
            )

            assertThat(member.id).isEqualTo(memberIdFixture(id))
            assertThat(member.email).isEqualTo(emailFixture(email))
            assertThat(member.password.value).isEqualTo(encodedPasswordFixture(password).value)
            assertThat(member.username).isEqualTo(usernameFixture(username))
            assertThat(member.nickname).isEqualTo(nicknameFixture(nickname))
            assertThat(member.role).isEqualTo(role)
            assertThat(member.registeredAt).isEqualTo(registeredAt)
        }
    }
}
