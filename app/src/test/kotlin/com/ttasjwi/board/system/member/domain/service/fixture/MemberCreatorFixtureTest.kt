package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.service.MemberCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("MemberCreatorFixture 테스트")
class MemberCreatorFixtureTest {

    private lateinit var memberCreator: MemberCreator

    @BeforeEach
    fun setup() {
        memberCreator = MemberCreatorFixture()
    }

    @Nested
    @DisplayName("create: 가입되지 않은 일반 사용자를 생성한다")
    inner class Create {

        @Test
        @DisplayName("가입되지 않은 일반 사용자(USER)가 생성되고, id는 null 이다.")
        fun test() {
            val email = "hello@gmail.com"
            val username = "kyakyakya"
            val nickname = "캬캬캬"
            val rawPassword = "1111"
            val currentTime = appDateTimeFixture()
            val member = memberCreator.create(
                email = email,
                username = username,
                nickname = nickname,
                password = rawPassword,
                currentTime = currentTime,
            )

            assertThat(member).isNotNull()
            assertThat(member.id).isNotNull()
            assertThat(member.email).isEqualTo(email)
            assertThat(member.username).isEqualTo(username)
            assertThat(member.nickname).isEqualTo(nickname)
            assertThat(member.password).isEqualTo(rawPassword)
            assertThat(member.role).isEqualTo(Role.USER)
            assertThat(member.registeredAt).isEqualTo(currentTime)
        }
    }
}
