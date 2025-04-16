package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("User: 사용자(회원)")
class UserTest {

    @Nested
    @DisplayName("create: 사용자를 생성한다")
    inner class Create {

        @Test
        @DisplayName("생성하면 일반 사용자 권한을 가진 사용자가 id 없는 상태로 생성된다.")
        fun test() {
            val userId = 1234L
            val email = "jello@gmail.com"
            val username = "jello"
            val nickname = "헬로"
            val rawPassword = "1111"
            val registeredAt = appDateTimeFixture()
            val user = User.create(
                userId = userId,
                email = email,
                username = username,
                nickname = nickname,
                password = rawPassword,
                registeredAt = registeredAt
            )

            assertThat(user).isNotNull()
            assertThat(user.userId).isNotNull()
            assertThat(user.email).isEqualTo(email)
            assertThat(user.username).isEqualTo(username)
            assertThat(user.nickname).isEqualTo(nickname)
            assertThat(user.password).isEqualTo(rawPassword)
            assertThat(user.role).isEqualTo(Role.USER)
            assertThat(user.registeredAt).isEqualTo(registeredAt)
        }
    }

    @Nested
    @DisplayName("restore: 값으로부터 회원을 복원한다.")
    inner class Restore {

        @Test
        fun restoreTest() {
            val id = 144L
            val email = "ttasjwi@test.com"
            val password = "1234"
            val username = "ttasjwi"
            val nickname = "땃쥐"
            val roleName = "USER"
            val registeredAt = appDateTimeFixture().toLocalDateTime()
            val user = User.restore(
                userId = id,
                email = email,
                password = password,
                username = username,
                nickname = nickname,
                roleName = roleName,
                registeredAt = registeredAt,
            )

            assertThat(user).isNotNull
            assertThat(user.userId).isEqualTo(id)
            assertThat(user.email).isEqualTo(email)
            assertThat(user.password).isEqualTo(password)
            assertThat(user.username).isEqualTo(username)
            assertThat(user.nickname).isEqualTo(nickname)
            assertThat(user.role).isEqualTo(Role.restore(roleName))
            assertThat(user.registeredAt.toLocalDateTime()).isEqualTo(registeredAt)
        }
    }


    @Nested
    @DisplayName("toString")
    inner class ToString {

        @Test
        @DisplayName("toString 이 의도한 대로 문자열을 반환하는 지 테스트")
        fun test() {
            val user = userFixture()

            assertThat(user.toString()).isEqualTo(
                "User(userId=${user.userId}, email=${user.email}, password=[!!SECRET!!], username=${user.username}, nickname=${user.nickname}, role=${user.role}, registeredAt=${user.registeredAt})"
            )
        }
    }

}
