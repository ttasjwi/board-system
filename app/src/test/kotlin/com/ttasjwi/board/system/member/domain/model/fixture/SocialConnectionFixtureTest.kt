package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.SocialService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialConnection 픽스쳐 테스트")
class SocialConnectionFixtureTest {

    @Nested
    @DisplayName("socialConnectionFixtureNotSaved : 아이디가 발급되지 않은(저장되지 않은) 소셜연동")
    inner class NotSaved {

        @Test
        @DisplayName("디폴트")
        fun test1() {
            // given
            // when
            val socialConnection = socialConnectionFixtureNotSaved()

            // then
            assertThat(socialConnection.id).isNull()
            assertThat(socialConnection.memberId).isNotNull
            assertThat(socialConnection.socialServiceUser).isNotNull()
            assertThat(socialConnection.linkedAt).isNotNull()
        }

        @Test
        @DisplayName("커스텀 파라미터")
        fun test2() {
            // given
            val memberId = 3L
            val socialService = SocialService.KAKAO
            val socialServiceUserId = "a77fsdagg"
            val linkedAt = timeFixture(minute = 7)

            // when
            val socialConnection = socialConnectionFixtureNotSaved(
                memberId = memberId,
                socialService = socialService,
                socialServiceUserId = socialServiceUserId,
                linkedAt = linkedAt
            )

            // then
            assertThat(socialConnection.id).isNull()
            assertThat(socialConnection.memberId.value).isEqualTo(memberId)
            assertThat(socialConnection.socialServiceUser.service).isEqualTo(socialService)
            assertThat(socialConnection.socialServiceUser.userId).isEqualTo(socialServiceUserId)
            assertThat(socialConnection.linkedAt).isEqualTo(linkedAt)
        }
    }

    @Nested
    @DisplayName("socialConnectionFixtureSaved: 아이디가 발급된(저장된) 소셜연동")
    inner class Saved {

        @Test
        @DisplayName("디폴트")
        fun test1() {
            // given
            // when
            val socialConnection = socialConnectionFixtureSaved()

            // then
            assertThat(socialConnection.id).isNotNull
            assertThat(socialConnection.memberId).isNotNull
            assertThat(socialConnection.socialServiceUser).isNotNull
            assertThat(socialConnection.linkedAt).isNotNull
        }

        @Test
        @DisplayName("커스텀 파라미터")
        fun test2() {
            // given
            val id = 2L
            val memberId = 133L
            val socialService = SocialService.NAVER
            val socialServiceUserId = "adfadf7ad7"
            val linkedAt = timeFixture(minute = 13)

            // when
            val socialConnection = socialConnectionFixtureSaved(
                id = id,
                memberId = memberId,
                socialService = socialService,
                socialServiceUserId = socialServiceUserId,
                linkedAt = linkedAt
            )

            // then
            assertThat(socialConnection.id!!.value).isEqualTo(id)
            assertThat(socialConnection.memberId.value).isEqualTo(memberId)
            assertThat(socialConnection.socialServiceUser.service).isEqualTo(socialService)
            assertThat(socialConnection.socialServiceUser.userId).isEqualTo(socialServiceUserId)
            assertThat(socialConnection.linkedAt).isEqualTo(linkedAt)
        }
    }

}
