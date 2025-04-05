package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.global.auth.fixture.authMemberFixture
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

    @Nested
    @DisplayName("changeNewRefreshToken: 리프레시토큰 홀더에 있는 기존 토큰을 제거하고 새 리프레시토큰을 추가한다")
    inner class ChangeRefreshToken {

        @Test
        @DisplayName("기존 토큰이 제거되고 새 토큰으로 대체되어야 한다.")
        fun test() {
            // given
            val previousToken = refreshTokenFixture(memberId = 1L, refreshTokenId = "token1234")
            val newToken = refreshTokenFixture(memberId = 1L, refreshTokenId = "token4321")
            val holder = refreshTokenHolderFixture(
                memberId = 1L,
                tokens = mutableMapOf(previousToken.refreshTokenId to previousToken)
            )

            // when
            val changedRefreshTokenHolder = refreshTokenHolderManagerFixture.changeRefreshToken(holder, previousToken, newToken)

            // then
            val tokens = changedRefreshTokenHolder.getTokens()

            assertThat(changedRefreshTokenHolder.authMember).isEqualTo(holder.authMember)
            assertThat(tokens).hasSize(1)
            assertThat(tokens[previousToken.refreshTokenId]).isNull()
            assertThat(tokens[newToken.refreshTokenId]).isEqualTo(newToken)
        }
    }
}
