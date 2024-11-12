package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.Role
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import com.ttasjwi.board.system.member.domain.model.fixture.nicknameFixture
import com.ttasjwi.board.system.member.domain.model.fixture.usernameFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("authMemberFixture(): 테스트용 인증사용자(AuthMember) 인스턴스를 생성한다.")
class AuthMemberFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값을 가지고 있다.")
    fun test1() {
        val authMember = authMemberFixture()
        assertThat(authMember.memberId).isNotNull()
        assertThat(authMember.email).isNotNull()
        assertThat(authMember.username).isNotNull()
        assertThat(authMember.nickname).isNotNull()
        assertThat(authMember.role).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 속성들을 변경해서 간단하게 인스턴스를 생성할 수 있다.")
    fun test2() {
        val memberId = 1L
        val email = "test@gmail.com"
        val username = "jest"
        val nickname = "캬루"
        val role = Role.USER
        val testEmail = authMemberFixture(
            memberId = memberId,
            email = email,
            username = username,
            nickname = nickname,
            role = role
        )

        assertThat(testEmail.memberId).isEqualTo(memberIdFixture(memberId))
        assertThat(testEmail.email).isEqualTo(emailFixture(email))
        assertThat(testEmail.username).isEqualTo(usernameFixture(username))
        assertThat(testEmail.nickname).isEqualTo(nicknameFixture(nickname))
        assertThat(testEmail.role).isEqualTo(role)
    }
}
