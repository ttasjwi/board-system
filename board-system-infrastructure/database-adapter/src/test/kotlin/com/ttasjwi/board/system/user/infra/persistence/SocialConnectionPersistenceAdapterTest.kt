package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import com.ttasjwi.board.system.user.domain.model.SocialService
import com.ttasjwi.board.system.user.domain.model.fixture.socialConnectionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[user-database-adapter] SocialConnectionPersistenceAdapter 테스트")
class SocialConnectionPersistenceAdapterTest : DataBaseIntegrationTest() {

    @DisplayName("FindBySocialServiceUserOrNull: 소셜 서비스 사용자로 소셜 연동을 조회할 수 있다")
    @Nested
    inner class FindBySocialServiceUserOrNull {

        @Test
        @DisplayName("저장된 소셜연동의 소셜서비스 사용자로, 소셜 연동을 조회할 수 있다.")
        fun findSuccessTest() {
            // given
            val socialConnection = userSocialConnectionPersistenceAdapter.save(socialConnectionFixture())
            flushAndClearEntityManager()

            // when
            val findSocialConnection = userSocialConnectionPersistenceAdapter.read(
                socialService = socialConnection.socialService,
                socialServiceUserId = socialConnection.socialServiceUserId,
            )!!

            // then
            assertThat(findSocialConnection.socialConnectionId).isEqualTo(socialConnection.socialConnectionId)
            assertThat(findSocialConnection.userId).isEqualTo(socialConnection.userId)
            assertThat(findSocialConnection.socialService).isEqualTo(socialConnection.socialService)
            assertThat(findSocialConnection.socialServiceUserId).isEqualTo(socialConnection.socialServiceUserId)
            assertThat(findSocialConnection.linkedAt).isEqualTo(socialConnection.linkedAt)
        }

        @Test
        @DisplayName("못 찾으면 Null 반환됨")
        fun findNullTest() {
            // given
            val socialService = SocialService.KAKAO
            val socialServiceUserId = "024756ggf"

            // when
            val socialConnection = userSocialConnectionPersistenceAdapter.read(
                socialService = socialService,
                socialServiceUserId = socialServiceUserId,
            )

            // then
            assertThat(socialConnection).isNull()
        }
    }
}
