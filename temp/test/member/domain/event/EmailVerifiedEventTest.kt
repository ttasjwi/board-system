package com.ttasjwi.board.system.member.domain.event

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.event.fixture.emailVerifiedEventFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerifiedEvent 테스트")
class EmailVerifiedEventTest {

    @Test
    @DisplayName("이벤트 발생시점은 이메일 인증 시각과 같다")
    fun test1() {
        val verifiedAt = appDateTimeFixture()
        val event = emailVerifiedEventFixture(
            verifiedAt = verifiedAt
        )
        assertThat(event.occurredAt).isEqualTo(verifiedAt)
        assertThat(event.data.verifiedAt).isEqualTo(verifiedAt)
    }

    @Test
    @DisplayName("생성시점에 전달한 데이터를 가진다")
    fun test2() {
        val email = "kyaru@gmail.com"
        val verifiedAt = appDateTimeFixture(minute = 7)
        val verificationExpiresAt = appDateTimeFixture(minute = 37)

        val event = emailVerifiedEventFixture(
            email = email,
            verifiedAt = verifiedAt,
            verificationExpiresAt = verificationExpiresAt
        )
        val data = event.data

        assertThat(data.email).isEqualTo(email)
        assertThat(data.verifiedAt).isEqualTo(verifiedAt)
        assertThat(data.verificationExpiresAt).isEqualTo(verificationExpiresAt)
    }

}
