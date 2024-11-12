package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.member.domain.model.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AuthMember 테스트")
class AuthMemberTest {

    @Nested
    @DisplayName("restore: 값들로부터 AuthMember 를 복원한다.")
    inner class Restore {

        @Test
        @DisplayName("복원된 AuthMember는 내부적으로 각각의 값 필드를 복원하여 가지고 있다.")
        fun test() {
            val memberId = 1L
            val email = "test@test.com"
            val username = "username"
            val nickname = "nickname"
            val roleName = "USER"

            val restoredAuthMember = AuthMember.restore(memberId, email, username, nickname, roleName)

            assertThat(restoredAuthMember).isInstanceOf(AuthMember::class.java)
            assertThat(restoredAuthMember.memberId).isEqualTo(MemberId.restore(memberId))
            assertThat(restoredAuthMember.email).isEqualTo(Email.restore(email))
            assertThat(restoredAuthMember.username).isEqualTo(Username.restore(username))
            assertThat(restoredAuthMember.nickname).isEqualTo(Nickname.restore(nickname))
            assertThat(restoredAuthMember.role).isEqualTo(Role.restore(roleName))
        }
    }

    @Nested
    @DisplayName("Equals & HashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("모든 값이 같으면 동등하다")
        fun testEquals() {
            // given
            val authMember = authMemberFixture()
            val other = authMemberFixture()

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(authMember.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("참조가 같으면 동등하다")
        fun testSameReference() {
            // given
            val authMember = authMemberFixture()
            val other = authMember

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(authMember.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("AuthMember가 아니면 동등하지 않다")
        fun testDifferentType() {
            // given
            val authMember = authMemberFixture()
            val other = 1L

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isFalse()
        }


        @Test
        @DisplayName("비교대상이 null 이면 동등하지 않다")
        fun testNull() {
            // given
            val authMember = authMemberFixture()
            val other = null

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isFalse()
        }


        @Test
        @DisplayName("Email 이 다르면 동등하지 않다")
        fun testDifferentEmail() {
            // given
            val authMember = authMemberFixture(email ="hello@gmail.com")
            val other = authMemberFixture(email= "jello@gmail.com")

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("Username 이 다르면 동등하지 않다")
        fun testDifferentUsername() {
            // given
            val authMember = authMemberFixture(username ="pppp")
            val other = authMemberFixture(username ="eeee")

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("Nickname 이 다르면 동등하지 않다")
        fun testDifferentNickname() {
            // given
            val authMember = authMemberFixture(nickname = "molu")
            val other = authMemberFixture(nickname = "jolu")

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isFalse()
        }


        @Test
        @DisplayName("역할이 다르면 동등하지 않다")
        fun testDifferentRole() {
            // given
            val authMember = authMemberFixture(role = Role.USER)
            val other = authMemberFixture(role = Role.ADMIN)

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isFalse()
        }
    }

    @Test
    @DisplayName("toString: 디버깅용 문자열")
    fun testToString() {
        val id = 1L
        val email = "test@test.com"
        val username = "username"
        val nickname = "nickname"
        val role = Role.USER

        val restoredAuthMember = authMemberFixture(id, email, username, nickname, role)

        assertThat(restoredAuthMember.toString()).isEqualTo(
            "AuthMember(memberId=${MemberId.restore(id)}, email=${
                Email.restore(
                    email
                )
            }, username=${Username.restore(username)}, nickname=${Nickname.restore(nickname)}, role=${role})"
        )
    }
}
