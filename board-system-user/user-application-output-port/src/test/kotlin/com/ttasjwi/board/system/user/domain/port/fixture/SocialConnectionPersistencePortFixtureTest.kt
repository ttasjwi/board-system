package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialServiceUserFixture
import com.ttasjwi.board.system.user.domain.port.SocialConnectionPersistencePort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialConnectionStorage 픽스쳐 테스트")
class SocialConnectionPersistencePortFixtureTest {

    private lateinit var socialConnectionPersistencePortFixture: SocialConnectionPersistencePort

    @BeforeEach
    fun setup() {
        socialConnectionPersistencePortFixture = SocialConnectionPersistencePortFixture()
    }

    @DisplayName("FindBySocialServiceUserOrNull: 소셜서비스 사용자로 소셜 연동을 조회할 수 있다")
    @Nested
    inner class FindBySocialServiceUserOrNull {

        @Test
        @DisplayName("저장된 소셜연동의 소셜서비스 사용자로, 소셜 연동을 조회할 수 있다.")
        fun findSuccessTest() {
            // given
            val socialConnection = socialConnectionPersistencePortFixture.save(socialConnectionFixture())

            // when
            val findSocialConnection = socialConnectionPersistencePortFixture.findBySocialServiceUserOrNull(
                socialConnection.socialServiceUser
            )!!

            // then
            assertThat(findSocialConnection.socialConnectionId).isNotNull()
            assertThat(findSocialConnection.socialConnectionId).isEqualTo(socialConnection.socialConnectionId)
            assertThat(findSocialConnection.userId).isEqualTo(socialConnection.userId)
            assertThat(findSocialConnection.socialServiceUser).isEqualTo(socialConnection.socialServiceUser)
            assertThat(findSocialConnection.linkedAt).isEqualTo(socialConnection.linkedAt)
        }

        @Test
        @DisplayName("못 찾으면 Null 반환됨")
        fun findNullTest() {
            // given
            val socialProviderUser = socialServiceUserFixture()

            // when
            val socialConnection =
                socialConnectionPersistencePortFixture.findBySocialServiceUserOrNull(socialProviderUser)

            // then
            assertThat(socialConnection).isNull()
        }
    }
}
