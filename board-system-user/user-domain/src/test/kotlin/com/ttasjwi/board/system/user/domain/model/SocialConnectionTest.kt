package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialConnectionFixture
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
            val userId = 174L
            val socialServiceName = "NAVER"
            val socialServiceUserId = "a7a7dfa7"
            val currentTime = appDateTimeFixture(minute = 4)

            // when
            val socialConnection = SocialConnection.create(
                socialConnectionId = socialConnectionId,
                userId = userId,
                socialServiceUser = SocialServiceUser.restore(socialServiceName, socialServiceUserId),
                currentTime = currentTime
            )

            // then
            assertThat(socialConnection.socialConnectionId).isEqualTo(socialConnectionId)
            assertThat(socialConnection.userId).isEqualTo(userId)
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
            val userId = 174L
            val socialServiceName = "NAVER"
            val socialServiceUserId = "a7a7dfa7"
            val linkedAt = appDateTimeFixture(minute = 4).toLocalDateTime()

            // when
            val socialConnection = SocialConnection.restore(
                socialConnectionId = socialConnectionId,
                userId = userId,
                socialServiceName = socialServiceName,
                socialServiceUserId = socialServiceUserId,
                linkedAt = linkedAt
            )

            // then
            assertThat(socialConnection.socialConnectionId).isEqualTo(socialConnectionId)
            assertThat(socialConnection.userId).isEqualTo(userId)
            assertThat(socialConnection.socialServiceUser).isEqualTo(
                SocialServiceUser.restore(
                    socialServiceName,
                    socialServiceUserId
                )
            )
            assertThat(socialConnection.linkedAt.toLocalDateTime()).isEqualTo(linkedAt)
        }
    }

    @Test
    @DisplayName("toString(): 디버깅용 문자열을 반환한다.")
    fun toStringTest() {
        // given
        val socialConnectionId = 35L
        val userId = 174L
        val socialService = SocialService.NAVER
        val socialServiceUserId = "a7a7dfa7"
        val linkedAt = appDateTimeFixture(minute = 4)

        val socialConnection = socialConnectionFixture(
            socialConnectionId = socialConnectionId,
            userId = userId,
            socialService = socialService,
            socialServiceUserId = socialServiceUserId,
            linkedAt = linkedAt
        )

        // when
        val str = socialConnection.toString()

        // then
        assertThat(str).isEqualTo("SocialConnection(socialConnectionId=$socialConnectionId, userId=$userId, socialServiceUser=${socialConnection.socialServiceUser}, linkedAt=$linkedAt)")
    }
}
