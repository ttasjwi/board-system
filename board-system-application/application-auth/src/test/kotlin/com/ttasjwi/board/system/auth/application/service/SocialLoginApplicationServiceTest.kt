package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.mapper.SocialLoginCommandMapper
import com.ttasjwi.board.system.auth.application.processor.SocialLoginProcessor
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.auth.domain.service.fixture.*
import com.ttasjwi.board.system.core.application.fixture.TransactionRunnerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.SocialService
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixtureNotRegistered
import com.ttasjwi.board.system.member.domain.model.fixture.socialConnectionFixtureNotSaved
import com.ttasjwi.board.system.member.domain.service.fixture.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialLoginApplicationService: 소셜로그인 요청을 받아 처리하는 애플리케이션 서비스")
class SocialLoginApplicationServiceTest {

    private lateinit var applicationService: SocialLoginApplicationService
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var memberStorageFixture: MemberStorageFixture
    private lateinit var socialConnectionStorageFixture: SocialConnectionStorageFixture
    private lateinit var usernameCreatorFixture: UsernameCreatorFixture
    private lateinit var nicknameCreatorFixture: NicknameCreatorFixture
    private lateinit var refreshTokenHolderStorageFixture: RefreshTokenHolderStorageFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        memberStorageFixture = MemberStorageFixture()
        socialConnectionStorageFixture = SocialConnectionStorageFixture()
        usernameCreatorFixture = UsernameCreatorFixture()
        nicknameCreatorFixture = NicknameCreatorFixture()
        refreshTokenHolderStorageFixture = RefreshTokenHolderStorageFixture()
        applicationService = SocialLoginApplicationService(
            commandMapper = SocialLoginCommandMapper(
                emailCreator = EmailCreatorFixture(),
                timeManager = timeManagerFixture
            ),
            processor = SocialLoginProcessor(
                memberFinder = memberStorageFixture,
                socialConnectionCreator = SocialConnectionCreatorFixture(),
                socialConnectionStorage = socialConnectionStorageFixture,
                passwordManager = PasswordManagerFixture(),
                usernameCreator = usernameCreatorFixture,
                nicknameCreator = nicknameCreatorFixture,
                memberCreator = MemberCreatorFixture(),
                memberAppender = memberStorageFixture,
                authMemberCreator = AuthMemberCreatorFixture(),
                accessTokenManager = AccessTokenManagerFixture(),
                refreshTokenManager = RefreshTokenManagerFixture(),
                refreshTokenHolderFinder = refreshTokenHolderStorageFixture,
                refreshTokenHolderManager = RefreshTokenHolderManagerFixture(),
                refreshTokenHolderAppender = refreshTokenHolderStorageFixture
            ),
            transactionRunner = TransactionRunnerFixture()
        )
    }

    @Test
    @DisplayName("로그인처리가 잘 되는 지 테스트")
    fun test() {
        // given
        val socialServiceName = "google"
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val currentTime = timeFixture(minute = 5)
        timeManagerFixture.changeCurrentTime(currentTime)

        val member = memberStorageFixture.save(memberFixtureNotRegistered(email = email))
        socialConnectionStorageFixture.save(
            socialConnectionFixtureNotSaved(
                memberId = member.id!!.value,
                socialService = SocialService.GOOGLE,
                socialServiceUserId = socialServiceUserId,
                linkedAt = timeFixture(minute = 3)
            )
        )

        val request = SocialLoginRequest(
            socialServiceName = socialServiceName,
            socialServiceUserId = socialServiceUserId,
            email = email,
        )

        // when
        val result = applicationService.socialLogin(request)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
        assertThat(result.memberCreated).isFalse()
        assertThat(result.createdMember).isNull()
    }

}
