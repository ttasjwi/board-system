package com.ttasjwi.board.system.domain.member.event.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerifiedEventFixture 테스트")
class EmailVerifiedEventFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값들을 가지고 있다.")
    fun test1() {
        val event = emailVerifiedEventFixture()
        val data = event.data

        assertThat(event.occurredAt).isNotNull()
        assertThat(data.email).isNotNull()
        assertThat(data.verifiedAt).isNotNull()
        assertThat(data.verificationExpiresAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 필드를 지정할 수 있다.")
    fun test2() {
        val email = "fire@gmail.com"
        val verifiedAt = appDateTimeFixture(minute = 11)
        val verificationExpiresAt = appDateTimeFixture(minute = 41)

        val event = emailVerifiedEventFixture(
            email = email,
            verifiedAt = verifiedAt,
            verificationExpiresAt = verificationExpiresAt
        )
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(verifiedAt)
        assertThat(data.email).isEqualTo(email)
        assertThat(data.verifiedAt).isEqualTo(verifiedAt)
        assertThat(data.verificationExpiresAt).isEqualTo(verificationExpiresAt)
    }
}
