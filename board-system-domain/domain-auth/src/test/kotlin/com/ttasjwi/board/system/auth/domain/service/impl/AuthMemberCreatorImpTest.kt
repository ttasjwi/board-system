package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.service.AuthMemberCreator
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixtureRegistered
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AuthMemberCreatorImpl 테스트")
class AuthMemberCreatorImpTest {

    private lateinit var authMemberCreator: AuthMemberCreator

    @BeforeEach
    fun setup() {
        authMemberCreator = AuthMemberCreatorImpl()
    }

    @Test
    @DisplayName("create: AuthMember 를 생성한다")
    fun testCreate() {
        // given
        val member = memberFixtureRegistered()

        // when
        val authMember = authMemberCreator.create(member)

        // then
        assertThat(authMember.memberId).isEqualTo(member.id)
        assertThat(authMember.email).isEqualTo(member.email)
        assertThat(authMember.username).isEqualTo(member.username)
        assertThat(authMember.nickname).isEqualTo(member.nickname)
        assertThat(authMember.role).isEqualTo(member.role)
    }
}
