package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.socialServiceUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialConnectionCreator Fixture 테스트")
class SocialConnectionCreatorFixtureTest {

    private lateinit var socialConnectionCreatorFixture: SocialConnectionCreatorFixture

    @BeforeEach
    fun setup() {
        socialConnectionCreatorFixture = SocialConnectionCreatorFixture()
    }

    @Test
    @DisplayName("create: 소셜 연동을 생성한다.")
    fun test() {
        // given
        val memberId = 1234556L
        val providerUser = socialServiceUserFixture()
        val currentTime = appDateTimeFixture(minute = 7)

        // when
        val socialConnection = socialConnectionCreatorFixture.create(
            memberId = memberId,
            socialServiceUser = providerUser,
            currentTime = currentTime,
        )

        // then
        assertThat(socialConnection.id).isNotNull()
        assertThat(socialConnection.memberId).isEqualTo(memberId)
        assertThat(socialConnection.socialServiceUser).isEqualTo(providerUser)
        assertThat(socialConnection.linkedAt).isEqualTo(currentTime)
    }
}
