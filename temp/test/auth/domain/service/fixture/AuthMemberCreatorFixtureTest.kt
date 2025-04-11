package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AuthMemberCreator 픽스쳐 테스트")
class AuthMemberCreatorFixtureTest {

    private lateinit var authMemberCreatorFixture: AuthMemberCreatorFixture

    @BeforeEach
    fun setup() {
        authMemberCreatorFixture = AuthMemberCreatorFixture()
    }


    @Test
    @DisplayName("create: AuthMember 를 생성한다")
    fun testCreate() {
        // given
        val member = memberFixture()

        // when
        val authMember = authMemberCreatorFixture.create(member)

        // then
        assertThat(authMember.memberId).isEqualTo(member.id)
        assertThat(authMember.role).isEqualTo(member.role)
    }
}
