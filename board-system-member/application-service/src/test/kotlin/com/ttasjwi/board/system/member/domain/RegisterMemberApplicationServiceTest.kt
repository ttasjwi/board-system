package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.mapper.RegisterMemberCommandMapper
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.member.domain.policy.fixturer.NicknamePolicyFixture
import com.ttasjwi.board.system.member.domain.policy.fixturer.PasswordPolicyFixture
import com.ttasjwi.board.system.member.domain.policy.fixturer.UsernamePolicyFixture
import com.ttasjwi.board.system.member.domain.port.fixture.EmailFormatValidatePortFixture
import com.ttasjwi.board.system.member.domain.port.fixture.EmailVerificationPersistencePortFixture
import com.ttasjwi.board.system.member.domain.port.fixture.MemberPersistencePortFixture
import com.ttasjwi.board.system.member.domain.port.fixture.PasswordEncryptionPortFixture
import com.ttasjwi.board.system.member.domain.processor.RegisterMemberProcessor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RegisterMemberApplicationService: 회원가입 애플리케이션 서비스")
class RegisterMemberApplicationServiceTest {

    private lateinit var applicationService: RegisterMemberApplicationService
    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val timeManagerFixture = TimeManagerFixture()
        currentTime = appDateTimeFixture(minute = 6)
        timeManagerFixture.changeCurrentTime(currentTime)

        val memberPersistencePortFixture = MemberPersistencePortFixture()
        emailVerificationPersistencePortFixture = EmailVerificationPersistencePortFixture()

        applicationService = RegisterMemberApplicationService(
            commandMapper = RegisterMemberCommandMapper(
                timeManager = timeManagerFixture,
                emailFormatValidatePort = EmailFormatValidatePortFixture(),
                usernamePolicy = UsernamePolicyFixture(),
                nicknamePolicy = NicknamePolicyFixture(),
                passwordPolicy = PasswordPolicyFixture(),
            ),
            processor = RegisterMemberProcessor(
                memberPersistencePort = memberPersistencePortFixture,
                passwordEncryptionPort = PasswordEncryptionPortFixture(),
                emailVerificationPersistencePort = emailVerificationPersistencePortFixture,
            ),
        )
    }

    @Test
    @DisplayName("회원가입을 처리하고 그 결과를 반환한다.")
    fun test() {
        val email = "hello@gmail.com"
        emailVerificationPersistencePortFixture.save(
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
