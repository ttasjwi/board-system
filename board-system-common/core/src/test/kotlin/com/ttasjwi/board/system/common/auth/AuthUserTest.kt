package com.ttasjwi.board.system.common.auth

import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AuthUser 테스트")
class AuthUserTest {

    @Nested
    @DisplayName("create: 회원 아이디, Role 로부터 AuthMember 를 생성한다.")
    inner class Create {


        @Test
        @DisplayName("생성 시 전달한 memberId, Role 을 가지고 있다.")
        fun createTest() {
            // given
            val userId = 1L
            val role = Role.USER

            // when
            val createdAuthUser = AuthUser.create(userId, role)

            // then
            assertThat(createdAuthUser.userId).isEqualTo(userId)
            assertThat(createdAuthUser.role).isEqualTo(role)
        }
    }

    @Nested
    @DisplayName("restore: 값들로부터 AuthMember 를 복원한다.")
    inner class Restore {

        @Test
        @DisplayName("복원된 AuthMember는 내부적으로 각각의 값 필드를 복원하여 가지고 있다.")
        fun test() {
            val memberId = 1L
            val roleName = "USER"

            val restoredAuthUser = AuthUser.restore(memberId, roleName)

            assertThat(restoredAuthUser.userId).isEqualTo(memberId)
            assertThat(restoredAuthUser.role).isEqualTo(Role.restore(roleName))
        }
    }

    @Nested
    @DisplayName("Equals & HashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("모든 값이 같으면 동등하다")
        fun testEquals() {
            // given
            val authMember = authUserFixture()
            val other = authUserFixture()

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
            val authMember = authUserFixture()
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
            val authMember = authUserFixture()
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
            val authMember = authUserFixture()
            val other = null

            // when
            val equals = authMember.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("역할이 다르면 동등하지 않다")
        fun testDifferentRole() {
            // given
            val authMember = authUserFixture(role = Role.USER)
            val other = authUserFixture(role = Role.ADMIN)

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
        val role = Role.USER

        val restoredAuthMember = authUserFixture(id, role)

        assertThat(restoredAuthMember.toString()).isEqualTo(
            "AuthMember(memberId=${id}, role=${role})"
        )
    }
}
