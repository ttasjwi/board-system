package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialConnection: 사용자의 소셜서비스 연동")
class SocialConnectionTest {

    @Nested
    @DisplayName("create : SocialConnection 을 생성한다.")
    inner class CreateTest {


        @Test
        @DisplayName("생성이 잘 되는 지 테스트")
        fun test() {
            // given
            val socialConnectionId = 35L
            val memberId = 174L
            val socialServiceName = "NAVER"
            val socialServiceUserId = "a7a7dfa7"
            val currentTime = appDateTimeFixture(minute = 4)

            // when
            val socialConnection = SocialConnection.create(
                socialConnectionId = socialConnectionId,
                memberId = memberId,
                socialServiceUser = SocialServiceUser.restore(socialServiceName, socialServiceUserId),
                currentTime = currentTime
            )

            // then
            assertThat(socialConnection.socialConnectionId).isEqualTo(socialConnectionId)
            assertThat(socialConnection.memberId).isEqualTo(memberId)
            assertThat(socialConnection.socialServiceUser).isEqualTo(
                SocialServiceUser.restore(
                    socialServiceName,
                    socialServiceUserId
                )
            )
            assertThat(socialConnection.linkedAt).isEqualTo(currentTime)
        }

    }

    @Nested
    @DisplayName("restore: 값들로부터 SocialConnection 을 복원한다.")
    inner class Restore {

        @Test
        @DisplayName("복원이 잘 되는 지 테스트")
        fun test() {
            // given
            val socialConnectionId = 35L
            val memberId = 174L
            val socialServiceName = "NAVER"
            val socialServiceUserId = "a7a7dfa7"
            val linkedAt = appDateTimeFixture(minute = 4).toLocalDateTime()

            // when
            val socialConnection = SocialConnection.restore(
                socialConnectionId = socialConnectionId,
                memberId = memberId,
                socialServiceName = socialServiceName,
                socialServiceUserId = socialServiceUserId,
                linkedAt = linkedAt
            )

            // then
            assertThat(socialConnection.socialConnectionId).isEqualTo(socialConnectionId)
            assertThat(socialConnection.memberId).isEqualTo(memberId)
            assertThat(socialConnection.socialServiceUser).isEqualTo(
                SocialServiceUser.restore(
                    socialServiceName,
                    socialServiceUserId
                )
            )
            assertThat(socialConnection.linkedAt.toLocalDateTime()).isEqualTo(linkedAt)
        }
    }
}
