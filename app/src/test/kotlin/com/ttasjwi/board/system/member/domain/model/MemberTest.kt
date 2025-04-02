package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Member: 회원")
class MemberTest {

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
            val member = Member.restore(
                id = id,
                email = email,
                password = password,
                username = username,
                nickname = nickname,
                roleName = roleName,
                registeredAt = registeredAt,
            )

            assertThat(member).isNotNull
            assertThat(member.id).isEqualTo(id)
            assertThat(member.email).isEqualTo(email)
            assertThat(member.password.value).isEqualTo(password)
            assertThat(member.username).isEqualTo(username)
            assertThat(member.nickname).isEqualTo(nickname)
            assertThat(member.role).isEqualTo(Role.restore(roleName))
            assertThat(member.registeredAt.toLocalDateTime()).isEqualTo(registeredAt)
        }
    }


    @Nested
    @DisplayName("toString")
    inner class ToString {

        @Test
        @DisplayName("toString 이 의도한 대로 문자열을 반환하는 지 테스트")
        fun test() {
            val member = memberFixture()

            assertThat(member.toString()).isEqualTo(
                "Member(id=${member.id}, email=${member.email}, password=${member.password}, username=${member.username}, nickname=${member.nickname}, role=${member.role}, registeredAt=${member.registeredAt})"
            )
        }
    }

}
