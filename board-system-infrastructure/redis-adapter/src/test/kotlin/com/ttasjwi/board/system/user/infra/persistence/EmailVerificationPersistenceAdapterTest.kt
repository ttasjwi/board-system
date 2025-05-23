package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.test.RedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[user-redis-adapter] EmailVerificationPersistenceAdapter 테스트")
class EmailVerificationPersistenceAdapterTest : RedisAdapterTest() {

    companion object {
        private const val TEST_EMAIL = "test12345678@test.com"
    }

    @AfterEach
    fun removeResource() {
        emailVerificationPersistenceAdapter.remove(TEST_EMAIL)
    }

    @Test
    @DisplayName("이메일 인증을 저장하고 이메일로 다시 찾을 수 있다")
    fun saveAndFindTest() {
        val emailVerification = emailVerificationFixtureVerified(email = TEST_EMAIL)
        emailVerificationPersistenceAdapter.save(emailVerification, AppDateTime.now().plusMinutes(30))

        val findEmailVerification = emailVerificationPersistenceAdapter.findByEmailOrNull(emailVerification.email)!!

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
        val findEmailVerification = emailVerificationPersistenceAdapter.findByEmailOrNull("jest@jest.com")
        assertThat(findEmailVerification).isNull()
    }

    @Test
    @DisplayName("remove 테스트")
    fun remove() {
        val emailVerification = emailVerificationFixtureVerified(TEST_EMAIL)
        emailVerificationPersistenceAdapter.save(emailVerification, appDateTimeFixture())

        emailVerificationPersistenceAdapter.remove(emailVerification.email)

        val findEmailVerification = emailVerificationPersistenceAdapter.findByEmailOrNull(emailVerification.email)
        assertThat(findEmailVerification).isNull()
    }
}
