package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.fixture.memberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Member: 회원")
class MemberTest {

    @Nested
    @DisplayName("create: 회원을 생성한다")
    inner class Create {

        @Test
        @DisplayName("생성하면 일반 사용자 권한을 가진 사용자가 id 없는 상태로 생성된다.")
        fun test() {
            val memberId = 1234L
            val email = "jello@gmail.com"
            val username = "jello"
            val nickname = "헬로"
            val rawPassword = "1111"
            val registeredAt = appDateTimeFixture()
            val member = Member.create(
                memberId = memberId,
                email = email,
                username = username,
                nickname = nickname,
                password = rawPassword,
                registeredAt = registeredAt
            )

            assertThat(member).isNotNull()
            assertThat(member.memberId).isNotNull()
            assertThat(member.email).isEqualTo(email)
            assertThat(member.username).isEqualTo(username)
            assertThat(member.nickname).isEqualTo(nickname)
            assertThat(member.password).isEqualTo(rawPassword)
            assertThat(member.role).isEqualTo(Role.USER)
            assertThat(member.registeredAt).isEqualTo(registeredAt)
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
            val member = Member.restore(
                memberId = id,
                email = email,
                password = password,
                username = username,
                nickname = nickname,
                roleName = roleName,
                registeredAt = registeredAt,
            )

            assertThat(member).isNotNull
            assertThat(member.memberId).isEqualTo(id)
            assertThat(member.email).isEqualTo(email)
            assertThat(member.password).isEqualTo(password)
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
                "Member(memberId=${member.memberId}, email=${member.email}, password=[!!SECRET!!], username=${member.username}, nickname=${member.nickname}, role=${member.role}, registeredAt=${member.registeredAt})"
            )
        }
    }

}
