package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.mapper.LoginCommandMapper
import com.ttasjwi.board.system.auth.application.processor.LoginProcessor
import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.domain.service.fixture.*
import com.ttasjwi.board.system.common.application.fixture.TransactionRunnerFixture
import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixtureNotRegistered
import com.ttasjwi.board.system.member.domain.service.fixture.EmailCreatorFixture
import com.ttasjwi.board.system.member.domain.service.fixture.MemberStorageFixture
import com.ttasjwi.board.system.member.domain.service.fixture.PasswordManagerFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LoginApplicationService: 로그인 요청을 받아 처리하는 애플리케이션 서비스")
class LoginApplicationServiceTest {

    private lateinit var applicationService: LoginApplicationService
    private lateinit var savedMember: Member

    @BeforeEach
    fun setup() {
        val timeManagerFixture = TimeManagerFixture()
        timeManagerFixture.changeCurrentTime(timeFixture(minute = 10))

        val memberStorageFixture = MemberStorageFixture()

        savedMember = memberStorageFixture.save(
            memberFixtureNotRegistered(
                email = "hello@gmail.com",
                password = "1234",
                username = "username",
                nickname = "nickname",
                role = Role.USER,
                registeredAt = timeFixture(minute = 6)
            )
        )

        val refreshTokenHolderStorageFixture = RefreshTokenHolderStorageFixture()

        applicationService = LoginApplicationService(
            commandMapper = LoginCommandMapper(
                emailCreator = EmailCreatorFixture(),
                passwordManager = PasswordManagerFixture(),
                timeManager = timeManagerFixture
            ),
            processor = LoginProcessor(
                memberFinder = memberStorageFixture,
                passwordManager = PasswordManagerFixture(),
                authMemberCreator = AuthMemberCreatorFixture(),
                accessTokenManager = AccessTokenManagerFixture(),
                refreshTokenManager = RefreshTokenManagerFixture(),
                refreshTokenHolderFinder = refreshTokenHolderStorageFixture,
                refreshTokenHolderManager = RefreshTokenHolderManagerFixture(),
                refreshTokenHolderAppender = refreshTokenHolderStorageFixture,
                authEventCreator = AuthEventCreatorFixture()
            ),
            transactionRunner = TransactionRunnerFixture()
        )
    }

    @Test
    @DisplayName("login: 로그인 요청을 받아 로그인 처리 후, 그 결과를 반환한다.")
    fun test() {
        // given
        val request = LoginRequest(savedMember.email.value, "1234")

        // when
        val result = applicationService.login(request)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
    }
}
