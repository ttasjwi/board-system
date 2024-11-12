package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenHolderManager 픽스쳐 테스트")
class RefreshTokenHolderManagerFixtureTest {

    private lateinit var refreshTokenHolderManagerFixture: RefreshTokenHolderManagerFixture

    @BeforeEach
    fun setup() {
        refreshTokenHolderManagerFixture = RefreshTokenHolderManagerFixture()
    }

    @Nested
    @DisplayName("createRefreshTokenHolder: 빈 리프레시토큰 홀더를 생성한다")
    inner class CreateRefreshTokenHolder {

        @Test
        @DisplayName("동작 테스트")
        fun test() {
            // given
            val authMember = authMemberFixture()

            // when
            val holder = refreshTokenHolderManagerFixture.createRefreshTokenHolder(authMember)

            // then
            val tokens = holder.getTokens()

            assertThat(holder.authMember).isEqualTo(authMember)
            assertThat(tokens).isEmpty()
        }
    }

    @Nested
    @DisplayName("addNewRefreshToken: 리프레시토큰 홀더에 새 리프레시토큰을 추가한다")
    inner class AddNewRefreshToken {


        @Test
        @DisplayName("추가한 토큰을 가지고 있어야한다")
        fun test() {
            // given
            val holder = refreshTokenHolderFixture(
                memberId = 1L,
            )
            val refreshToken = refreshTokenFixture(memberId = 1L, refreshTokenId = "token1234")

            // when
            val addedRefreshTokenHolder = refreshTokenHolderManagerFixture.addNewRefreshToken(holder, refreshToken)

            // then
            val tokens = addedRefreshTokenHolder.getTokens()

            assertThat(addedRefreshTokenHolder.authMember).isEqualTo(holder.authMember)
            assertThat(tokens).hasSize(1)
            assertThat(tokens[refreshToken.refreshTokenId]).isEqualTo(refreshToken)
        }
    }
}
