package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("authUserFixture(): 테스트용 인증사용자(AuthUser) 인스턴스를 생성한다.")
class AuthUserFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값을 가지고 있다.")
    fun test1() {
        val authUser = authUserFixture()
        assertThat(authUser.userId).isNotNull()
        assertThat(authUser.role).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 속성들을 변경해서 간단하게 인스턴스를 생성할 수 있다.")
    fun test2() {
        val userId = 4L
        val role = Role.USER
        val testEmail = authUserFixture(
            userId = userId,
            role = role
        )
        assertThat(testEmail.userId).isEqualTo(userId)
        assertThat(testEmail.role).isEqualTo(role)
    }
}
