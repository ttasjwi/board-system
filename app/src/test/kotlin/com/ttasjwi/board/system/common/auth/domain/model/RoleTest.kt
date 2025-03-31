package com.ttasjwi.board.system.common.auth.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Role: 사용자의 역할")
class RoleTest {

    @Test
    @DisplayName("restore: 역할 이름으로부터 Role 인스턴스를 복원한다.")
    fun testRestore() {
        val role1 = Role.restore("USER")
        val role2 = Role.restore("ADMIN")
        val role3 = Role.restore("SYSTEM")
        val role4 = Role.restore("ROOT")
        assertThat(role1).isEqualTo(Role.USER)
        assertThat(role2).isEqualTo(Role.ADMIN)
        assertThat(role3).isEqualTo(Role.SYSTEM)
        assertThat(role4).isEqualTo(Role.ROOT)
    }

    @Nested
    @DisplayName("역할들 테스트(설명)")
    inner class DescribeRole {

        @Test
        @DisplayName("Role.USER 는 일반 사용자 역할을 의미한다.")
        fun testUser() {
            val roleUser = Role.USER
            assertThat(roleUser.name).isEqualTo("USER")
        }


        @Test
        @DisplayName("Role.ADMIN 은 관리자 역할을 의미한다.")
        fun testAdmin() {
            val roleAdmin = Role.ADMIN
            assertThat(roleAdmin.name).isEqualTo("ADMIN")
        }

        @Test
        @DisplayName("Role.SYSTEM 은 외부 프로세스로서 우리 서비스의 유지보수에 필요한 작업을 수행하는 역할을 의미한다.")
        fun testSystem() {
            val roleSystem = Role.SYSTEM
            assertThat(roleSystem.name).isEqualTo("SYSTEM")
        }


        @Test
        @DisplayName("Role.ROOT 은 최고 관리자 역할을 의미한다.")
        fun testRoot() {
            val roleRoot = Role.ROOT
            assertThat(roleRoot.name).isEqualTo("ROOT")
        }
    }

    @Test
    @DisplayName("toString: 디버깅을 위한 문자열 반환")
    fun testToString() {
        val role = Role.USER
        assertThat(role.toString()).isEqualTo("Role(name=${role.name})")
    }
}
