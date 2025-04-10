package com.ttasjwi.board.system.member.domain.port.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationPersistencePortFixture 테스트")
class EmailVerificationPersistencePortFixtureTest {

    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture

    @BeforeEach
    fun setup() {
        emailVerificationPersistencePortFixture = EmailVerificationPersistencePortFixture()
    }

    @Test
    @DisplayName("이메일 인증을 저장하고 이메일로 다시 찾을 수 있다")
    fun saveAndFind() {
        val emailVerification = emailVerificationFixtureVerified()
        emailVerificationPersistencePortFixture.save(emailVerification, appDateTimeFixture())

        val findEmailVerification = emailVerificationPersistencePortFixture.findByEmailOrNull(emailVerification.email)!!

        assertThat(findEmailVerification.email).isEqualTo(emailVerification.email)
        assertThat(findEmailVerification.code).isEqualTo(emailVerification.code)
        assertThat(findEmailVerification.codeCreatedAt).isEqualTo(emailVerification.codeCreatedAt)
        assertThat(findEmailVerification.codeExpiresAt).isEqualTo(emailVerification.codeExpiresAt)
        assertThat(findEmailVerification.verifiedAt).isEqualTo(emailVerification.verifiedAt)
        assertThat(findEmailVerification.verificationExpiresAt).isEqualTo(emailVerification.verificationExpiresAt)
    }

    @Test
    @DisplayName("이메일 인증을 찾지 못 했다면 null 이 반환된다")
    fun notFoundNull() {
        val findEmailVerification = emailVerificationPersistencePortFixture.findByEmailOrNull("bye@gmail.com")
        assertThat(findEmailVerification).isNull()
    }

    @Test
    @DisplayName("remove 테스트")
    fun remove() {
        val emailVerification = emailVerificationFixtureVerified()
        emailVerificationPersistencePortFixture.save(emailVerification, appDateTimeFixture())

        emailVerificationPersistencePortFixture.remove(emailVerification.email)

        val findEmailVerification = emailVerificationPersistencePortFixture.findByEmailOrNull(emailVerification.email)
        assertThat(findEmailVerification).isNull()
    }

}
