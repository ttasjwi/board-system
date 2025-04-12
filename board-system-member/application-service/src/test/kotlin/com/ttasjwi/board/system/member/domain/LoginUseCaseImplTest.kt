package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LoginApplicationUseCaseImpl: 로그인 요청을 받아 처리하는 애플리케이션 서비스")
class LoginUseCaseImplTest {

    private lateinit var useCase: LoginUseCaseImpl
    private lateinit var savedMember: Member

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        container.timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 10))

        savedMember = container.memberPersistencePortFixture.save(
            memberFixture(
                email = "hello@gmail.com",
                password = "1234",
                username = "username",
                nickname = "nickname",
                role = Role.USER,
                registeredAt = appDateTimeFixture(minute = 6)
            )
        )
        useCase = container.loginUseCase
    }

    @Test
    @DisplayName("login: 로그인 요청을 받아 로그인 처리 후, 그 결과를 반환한다.")
    fun test() {
        // given
        val request = LoginRequest(savedMember.email, "1234")

        // when
        val result = useCase.login(request)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenType).isEqualTo("Bearer")
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
    }
}
