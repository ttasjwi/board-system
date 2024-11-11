package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.service.AuthEventCreator
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AuthEventCreatorImpl 테스트")
class AuthEventCreatorImplTest {

    private lateinit var authEventCreator: AuthEventCreator

    @BeforeEach
    fun setup() {
        authEventCreator = AuthEventCreatorImpl()
    }


    @Nested
    @DisplayName("onLoginSuccess: 로그인됨 이벤트를 생성함")
    inner class OnLoginSuccess {

        @Test
        @DisplayName("로그인됨 이벤트가 생성된다")
        fun test() {
            // given
            val memberId = 1L
            val email = "firepunch@gmail.com"
            val username = "username"
            val nickname = "nickname"
            val role = Role.ADMIN
            val loggedInAt = timeFixture(minute = 5)

            val accessToken = accessTokenFixture(
                memberId = memberId,
                email = email,
                username = username,
                nickname = nickname,
                role = role,
                issuedAt = loggedInAt,
                tokenValue = "accessToken"
            )
            val refreshToken = refreshTokenFixture(
                memberId = memberId,
                refreshTokenId = "refreshTokenId1",
                tokenValue = "toeknValue1",
                issuedAt = loggedInAt
            )

            // when
            val event = authEventCreator.onLoginSuccess(
                accessToken = accessToken,
                refreshToken = refreshToken,
            )

            // then
            val data = event.data

            assertThat(event.occurredAt).isEqualTo(loggedInAt)
            assertThat(data.accessToken).isEqualTo(accessToken.tokenValue)
            assertThat(data.accessTokenExpiresAt).isEqualTo(accessToken.expiresAt)
            assertThat(data.refreshToken).isEqualTo(refreshToken.tokenValue)
            assertThat(data.refreshTokenExpiresAt).isEqualTo(refreshToken.expiresAt)
        }
    }

}
