package com.ttasjwi.board.system.domain.member.external.db

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.member.model.fixture.emailVerificationFixtureVerified
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationStorage(Appender, Finder) 테스트")
class EmailVerificationStorageTest : IntegrationTest() {

    companion object {
        private const val TEST_EMAIL = "test12345678@test.com"
    }

    @AfterEach
    fun removeResource() {
        emailVerificationStorage.removeByEmail(TEST_EMAIL)
    }

    @Test
    @DisplayName("이메일 인증을 저장하고 이메일로 다시 찾을 수 있다")
    fun appendAndFind() {
        val emailVerification = emailVerificationFixtureVerified(email = TEST_EMAIL)
        emailVerificationStorage.append(emailVerification, AppDateTime.now().plusMinutes(30))

        val findEmailVerification = emailVerificationStorage.findByEmailOrNull(emailVerification.email)!!

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
        val findEmailVerification = emailVerificationStorage.findByEmailOrNull("jest@jest.com")
        assertThat(findEmailVerification).isNull()
    }

    @Test
    @DisplayName("remove 테스트")
    fun remove() {
        val emailVerification = emailVerificationFixtureVerified(TEST_EMAIL)
        emailVerificationStorage.append(emailVerification, appDateTimeFixture())

        emailVerificationStorage.removeByEmail(emailVerification.email)

        val findEmailVerification = emailVerificationStorage.findByEmailOrNull(emailVerification.email)
        assertThat(findEmailVerification).isNull()
    }

}
