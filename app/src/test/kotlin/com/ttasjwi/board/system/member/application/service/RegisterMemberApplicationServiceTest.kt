package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.application.mapper.RegisterMemberCommandMapper
import com.ttasjwi.board.system.member.application.processor.RegisterMemberProcessor
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.member.domain.policy.fixture.EmailFormatPolicyFixture
import com.ttasjwi.board.system.member.domain.service.fixture.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RegisterMemberApplicationService: 회원가입 애플리케이션 서비스")
class RegisterMemberApplicationServiceTest {

    private lateinit var applicationService: RegisterMemberApplicationService
    private lateinit var emailVerificationStorageFixture: EmailVerificationStorageFixture
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val timeManager = TimeManagerFixture()
        currentTime = appDateTimeFixture(minute = 6)
        timeManager.changeCurrentTime(currentTime)

        val memberStorageFixture = MemberStorageFixture()
        emailVerificationStorageFixture = EmailVerificationStorageFixture()

        applicationService = RegisterMemberApplicationService(
            commandMapper = RegisterMemberCommandMapper(
                emailFormatPolicy = EmailFormatPolicyFixture(),
                passwordManager = PasswordManagerFixture(),
                usernameManager = UsernameManagerFixture(),
                nicknameManager = NicknameManagerFixture(),
                timeManager = timeManager,
            ),
            processor = RegisterMemberProcessor(
                memberFinder = memberStorageFixture,
                emailVerificationFinder = emailVerificationStorageFixture,
                emailVerificationHandler = EmailVerificationHandlerFixture(),
                emailVerificationAppender = emailVerificationStorageFixture,
                memberCreator = MemberCreatorFixture(),
                memberAppender = memberStorageFixture,
                memberEventCreator = MemberEventCreatorFixture()
            ),
        )
    }

    @Test
    @DisplayName("회원가입을 처리하고 그 결과를 반환한다.")
    fun test() {
        val email = "hello@gmail.com"
        emailVerificationStorageFixture.append(
            emailVerificationFixtureVerified(
                email = email,
                code = "code",
                codeCreatedAt = appDateTimeFixture(minute = 0),
                codeExpiresAt = appDateTimeFixture(minute = 5),
                verifiedAt = appDateTimeFixture(minute = 3),
                verificationExpiresAt = appDateTimeFixture(minute = 33),
            ), appDateTimeFixture(minute = 33)
        )

        val request = RegisterMemberRequest(
            email = email,
            password = "1111",
            username = "testuser",
            nickname = "testnick",
        )

        val response = applicationService.register(request)

        assertThat(response.memberId).isNotNull()
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.username).isEqualTo(request.username)
        assertThat(response.nickname).isEqualTo(request.nickname)
        assertThat(response.role).isNotNull()
        assertThat(response.registeredAt).isEqualTo(currentTime.toZonedDateTime())
    }
}
