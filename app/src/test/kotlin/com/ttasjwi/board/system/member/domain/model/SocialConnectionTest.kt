package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.common.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialConnection: 사용자의 소셜서비스 연동")
class SocialConnectionTest {

    @Nested
    @DisplayName("restore: 값들로부터 SocialConnection 을 복원한다.")
    inner class Restore {

        @Test
        @DisplayName("복원이 잘 되는 지 테스트")
        fun test() {
            // given
            val id = 35L
            val memberId = 174L
            val socialServiceName = "NAVER"
            val socialServiceUserId = "a7a7dfa7"
            val linkedAt = timeFixture(minute = 4)

            // when
            val socialConnection = SocialConnection.restore(
                id = id,
                memberId = memberId,
                socialServiceName = socialServiceName,
                socialServiceUserId = socialServiceUserId,
                linkedAt = linkedAt
            )

            // then
            assertThat(socialConnection.id).isEqualTo(SocialConnectionId.restore(id))
            assertThat(socialConnection.memberId).isEqualTo(MemberId.restore(memberId))
            assertThat(socialConnection.socialServiceUser).isEqualTo(
                SocialServiceUser.restore(
                    socialServiceName,
                    socialServiceUserId
                )
            )
            assertThat(socialConnection.linkedAt).isEqualTo(linkedAt)
        }
    }
}
