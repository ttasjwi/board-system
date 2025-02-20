package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import com.ttasjwi.board.system.member.domain.model.fixture.socialServiceUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialConnectionCreatorImpl 테스트")
class SocialConnectionCreatorImplTest {

    private lateinit var socialConnectionCreator: SocialConnectionCreatorImpl

    @BeforeEach
    fun setup() {
        socialConnectionCreator = SocialConnectionCreatorImpl()
    }


    @Test
    @DisplayName("create: 소셜 연동을 생성한다.")
    fun test() {
        // given
        val memberId = memberIdFixture()
        val socialServiceUser = socialServiceUserFixture()
        val currentTime = timeFixture()

        // when
        val socialConnection = socialConnectionCreator.create(
            memberId = memberId,
            socialServiceUser = socialServiceUser,
            currentTime = currentTime,
        )

        // then
        assertThat(socialConnection.id).isNull()
        assertThat(socialConnection.memberId).isEqualTo(memberId)
        assertThat(socialConnection.socialServiceUser).isEqualTo(socialServiceUser)
        assertThat(socialConnection.linkedAt).isEqualTo(currentTime)
    }
}
