package com.ttasjwi.board.system.member.infra.persistence

import com.ttasjwi.board.system.member.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.member.domain.model.fixture.socialServiceUserFixture
import com.ttasjwi.board.system.member.infra.test.MemberDataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialConnectionStorageImpl 테스트")
class SocialConnectionPersistenceAdapterTest : MemberDataBaseIntegrationTest() {

    @DisplayName("FindBySocialServiceUserOrNull: 소셜 서비스 사용자로 소셜 연동을 조회할 수 있다")
    @Nested
    inner class FindBySocialServiceUserOrNull {

        @Test
        @DisplayName("저장된 소셜연동의 소셜서비스 사용자로, 소셜 연동을 조회할 수 있다.")
        fun findSuccessTest() {
            // given
            val socialConnection = socialConnectionPersistenceAdapter.save(socialConnectionFixture())
            flushAndClearEntityManager()

            // when
            val findSocialConnection = socialConnectionPersistenceAdapter.findBySocialServiceUserOrNull(
                socialConnection.socialServiceUser
            )!!

            // then
            assertThat(findSocialConnection.socialConnectionId).isEqualTo(socialConnection.socialConnectionId)
            assertThat(findSocialConnection.memberId).isEqualTo(socialConnection.memberId)
            assertThat(findSocialConnection.socialServiceUser).isEqualTo(socialConnection.socialServiceUser)
            assertThat(findSocialConnection.linkedAt).isEqualTo(socialConnection.linkedAt)
        }

        @Test
        @DisplayName("못 찾으면 Null 반환됨")
        fun findNullTest() {
            // given
            val socialServiceUser = socialServiceUserFixture()

            // when
            val socialConnection = socialConnectionPersistenceAdapter.findBySocialServiceUserOrNull(socialServiceUser)

            // then
            assertThat(socialConnection).isNull()
        }
    }
}
