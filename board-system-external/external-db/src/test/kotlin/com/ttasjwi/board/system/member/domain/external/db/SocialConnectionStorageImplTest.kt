package com.ttasjwi.board.system.member.domain.external.db

import com.ttasjwi.board.system.DbTest
import com.ttasjwi.board.system.member.domain.model.fixture.socialConnectionFixtureNotSaved
import com.ttasjwi.board.system.member.domain.model.fixture.socialServiceUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialConnectionStorageImpl 테스트")
class SocialConnectionStorageImplTest : DbTest() {

    @DisplayName("save 테스트")
    @Nested
    inner class Save {

        @Test
        @DisplayName("save 후 아이디가 생성된다")
        fun test() {
            // given
            val socialConnection = socialConnectionFixtureNotSaved()

            // when
            val savedSocialConnection = socialConnectionStorage.save(socialConnection)

            // then
            assertThat(savedSocialConnection.id).isNotNull
        }
    }


    @DisplayName("FindBySocialServiceUserOrNull: 소셜 서비스 사용자로 소셜 연동을 조회할 수 있다")
    @Nested
    inner class FindBySocialServiceUserOrNull {

        @Test
        @DisplayName("저장된 소셜연동의 소셜서비스 사용자로, 소셜 연동을 조회할 수 있다.")
        fun findSuccessTest() {
            // given
            val socialConnection = socialConnectionStorage.save(socialConnectionFixtureNotSaved())
            flushAndClearEntityManager()

            // when
            val findSocialConnection = socialConnectionStorage.findBySocialServiceUserOrNull(
                socialConnection.socialServiceUser
            )!!

            // then
            assertThat(findSocialConnection.id).isNotNull()
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
