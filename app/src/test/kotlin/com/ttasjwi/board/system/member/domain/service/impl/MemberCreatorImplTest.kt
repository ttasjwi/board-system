package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.service.MemberCreator
import com.ttasjwi.board.system.member.domain.service.PasswordManager
import com.ttasjwi.board.system.member.domain.service.fixture.PasswordManagerFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("MemberCreatorImpl: 회원을 생성한다")
class MemberCreatorImplTest {

    private lateinit var memberCreator: MemberCreator
    private lateinit var passwordManager: PasswordManager

    @BeforeEach
    fun setup() {
        passwordManager = PasswordManagerFixture()
        memberCreator = MemberCreatorImpl(passwordManager)
    }

    @Nested
    @DisplayName("create: 회원을 생성한다")
    inner class Create {

        @Test
        @DisplayName("생성하면 일반 사용자 권한을 가진 사용자가 id 없는 상태로 생성된다.")
        fun test() {
            val email = "jello@gmail.com"
            val username = "jello"
            val nickname = "헬로"
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
