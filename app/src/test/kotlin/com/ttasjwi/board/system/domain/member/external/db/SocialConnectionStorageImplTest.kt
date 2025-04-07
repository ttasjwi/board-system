package com.ttasjwi.board.system.domain.member.external.db

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.domain.member.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.domain.member.model.fixture.socialServiceUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional

@Transactional
@DisplayName("SocialConnectionStorageImpl 테스트")
class SocialConnectionStorageImplTest : IntegrationTest() {

    @DisplayName("FindBySocialServiceUserOrNull: 소셜 서비스 사용자로 소셜 연동을 조회할 수 있다")
    @Nested
    inner class FindBySocialServiceUserOrNull {

        @Test
        @DisplayName("저장된 소셜연동의 소셜서비스 사용자로, 소셜 연동을 조회할 수 있다.")
        fun findSuccessTest() {
            // given
            val socialConnection = socialConnectionStorage.save(socialConnectionFixture())
            flushAndClearEntityManager()

            // when
            val findSocialConnection = socialConnectionStorage.findBySocialServiceUserOrNull(
                socialConnection.socialServiceUser
            )!!

            // then
            assertThat(findSocialConnection.id).isEqualTo(socialConnection.id)
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
            val socialConnection = socialConnectionStorage.findBySocialServiceUserOrNull(socialServiceUser)

            // then
            assertThat(socialConnection).isNull()
        }
    }
}
