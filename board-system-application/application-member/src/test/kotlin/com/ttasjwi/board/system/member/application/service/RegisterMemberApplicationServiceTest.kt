package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.application.fixture.TransactionRunnerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.mapper.RegisterMemberCommandMapper
import com.ttasjwi.board.system.member.application.processor.RegisterMemberProcessor
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.member.domain.service.fixture.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

@DisplayName("RegisterMemberApplicationService: 회원가입 애플리케이션 서비스")
class RegisterMemberApplicationServiceTest {

    private lateinit var applicationService: RegisterMemberApplicationService
    private lateinit var emailVerificationStorageFixture: EmailVerificationStorageFixture
    private lateinit var currentTime: ZonedDateTime

    @BeforeEach
    fun setup() {
        val timeManager = TimeManagerFixture()
        currentTime = timeFixture(minute = 6)
        timeManager.changeCurrentTime(currentTime)

        val memberStorageFixture = MemberStorageFixture()
        emailVerificationStorageFixture = EmailVerificationStorageFixture()

        applicationService = RegisterMemberApplicationService(
            commandMapper = RegisterMemberCommandMapper(
                emailCreator = EmailCreatorFixture(),
                passwordManager = PasswordManagerFixture(),
                usernameCreator = UsernameCreatorFixture(),
                nicknameCreator = NicknameCreatorFixture(),
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
            transactionRunner = TransactionRunnerFixture()
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
                codeCreatedAt = timeFixture(minute = 0),
                codeExpiresAt = timeFixture(minute = 5),
                verifiedAt = timeFixture(minute = 3),
                verificationExpiresAt = timeFixture(minute = 33),
            ), timeFixture(minute = 33)
        )

        val request = RegisterMemberRequest(
            email = email,
            password = "1111",
            username = "testuser",
            nickname = "testnick",
        )

        val result = applicationService.register(request)

        assertThat(result.memberId).isNotNull()
        assertThat(result.email).isEqualTo(request.email)
        assertThat(result.username).isEqualTo(request.username)
        assertThat(result.nickname).isEqualTo(request.nickname)
        assertThat(result.role).isNotNull()
        assertThat(result.registeredAt).isEqualTo(currentTime)
    }
}
