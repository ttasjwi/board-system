package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderManager
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenHolderManagerImpl 테스트")
class RefreshTokenHolderManagerImplTest {

    private lateinit var refreshTokenHolderManager: RefreshTokenHolderManager

    @BeforeEach
    fun setup() {
        refreshTokenHolderManager = RefreshTokenHolderManagerImpl()
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
            val holder = refreshTokenHolderManager.createRefreshTokenHolder(authMember)

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
            val addedRefreshTokenHolder = refreshTokenHolderManager.addNewRefreshToken(holder, refreshToken)

            // then
            val tokens = addedRefreshTokenHolder.getTokens()

            assertThat(addedRefreshTokenHolder.authMember).isEqualTo(holder.authMember)
            assertThat(tokens).hasSize(1)
            assertThat(tokens[refreshToken.refreshTokenId]).isEqualTo(refreshToken)
        }


        @Test
        @DisplayName("홀더에 만료된 토큰이 있다면 제거하고, 추가한다.")
        fun caseRemoveExpiredTokens() {
            // given
            val refreshToken1 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId1",
                issuedAt = appDateTimeFixture(minute = 0), expiresAt = appDateTimeFixture(minute = 5)
            )
            val refreshToken2 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId2",
                issuedAt = appDateTimeFixture(minute = 1), expiresAt = appDateTimeFixture(minute = 6)
            )
            val refreshToken3 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId3",
                issuedAt = appDateTimeFixture(minute = 2), expiresAt = appDateTimeFixture(minute = 7)
            )
            val refreshToken4 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId4",
                issuedAt = appDateTimeFixture(minute = 6), expiresAt = appDateTimeFixture(minute = 11)
            ) // token2 의 만료시점과 같음.

            val holder = refreshTokenHolderFixture(
                memberId = 1L,
                tokens = mutableMapOf(
                    refreshToken1.refreshTokenId to refreshToken1,
                    refreshToken2.refreshTokenId to refreshToken2,
                    refreshToken3.refreshTokenId to refreshToken3
                )
            )

            // when
            val addedRefreshTokenHolder = refreshTokenHolderManager.addNewRefreshToken(holder, refreshToken4)

            // when
            val tokens = addedRefreshTokenHolder.getTokens()
            assertThat(tokens).hasSize(2)
            assertThat(tokens.values).containsExactlyInAnyOrder(refreshToken3, refreshToken4)
        }

        @Test
        @DisplayName(
            "최대 토큰 보유 가능 갯수인 ${RefreshTokenHolder.MAX_TOKEN_COUNT} 개째에서, " +
                    "새로 토큰을 추가하면 발급 일이 가장 빠른(오래된) 토큰을 만료시킨다."
        )
        fun caseMaxCount() {
            val tokens = mutableMapOf<String, RefreshToken>()

            val firstToken = refreshTokenFixture(
                memberId = 1L,
                refreshTokenId = "refreshToken1",
                issuedAt = appDateTimeFixture(minute = 1),
                expiresAt = appDateTimeFixture(dayOfMonth = 2, minute = 1)
            )
            tokens[firstToken.refreshTokenId] = firstToken

            for (i in 2..RefreshTokenHolder.MAX_TOKEN_COUNT) {
                val token = refreshTokenFixture(
                    memberId = 1L,
                    refreshTokenId = "refreshToken${i}",
                    issuedAt = appDateTimeFixture(minute = i),
                    expiresAt = appDateTimeFixture(dayOfMonth = 2, minute = i)
                )
                tokens[token.refreshTokenId] = token
            }
            val holder = refreshTokenHolderFixture(memberId = 1L, tokens = tokens)
            val newToken = refreshTokenFixture(
                memberId = 1L,
                refreshTokenId = "refreshTokenNew",
                issuedAt = appDateTimeFixture(minute = 50),
                expiresAt = appDateTimeFixture(dayOfMonth = 2, minute = 50)
            )

            // when
            val addedRefreshTokenHolder = refreshTokenHolderManager.addNewRefreshToken(holder, newToken)
            val findTokens = addedRefreshTokenHolder.getTokens()

            // when
            assertThat(findTokens).hasSize(RefreshTokenHolder.MAX_TOKEN_COUNT)
            assertThat(findTokens.values).doesNotContain(firstToken)
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
            val changedRefreshTokenHolder =
                refreshTokenHolderManager.changeRefreshToken(holder, previousToken, newToken)

            // then
            val tokens = changedRefreshTokenHolder.getTokens()

            assertThat(changedRefreshTokenHolder.authMember).isEqualTo(holder.authMember)
            assertThat(tokens).hasSize(1)
            assertThat(tokens[previousToken.refreshTokenId]).isNull()
            assertThat(tokens[newToken.refreshTokenId]).isEqualTo(newToken)
        }


        @Test
        @DisplayName("홀더에 만료된 토큰이 있다면 제거한 작업을 하고 대체한다.")
        fun caseRemoveExpiredTokens() {
            // given
            val oldToken1 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId1",
                issuedAt = appDateTimeFixture(minute = 0), expiresAt = appDateTimeFixture(minute = 5)
            )
            val oldToken2 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId2",
                issuedAt = appDateTimeFixture(minute = 1), expiresAt = appDateTimeFixture(minute = 6)
            )
            val previousToken = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "previous",
                issuedAt = appDateTimeFixture(minute = 2), expiresAt = appDateTimeFixture(minute = 7)
            )
            val newToken = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "newToken",
                issuedAt = appDateTimeFixture(minute = 6), expiresAt = appDateTimeFixture(minute = 11)
            )

            val holder = refreshTokenHolderFixture(
                memberId = 1L,
                tokens = mutableMapOf(
                    oldToken1.refreshTokenId to oldToken1,
                    oldToken2.refreshTokenId to oldToken2,
                    previousToken.refreshTokenId to previousToken
                )
            )

            // when
            val addedRefreshTokenHolder = refreshTokenHolderManager.changeRefreshToken(holder, previousToken, newToken)

            // when
            val tokens = addedRefreshTokenHolder.getTokens()
            assertThat(tokens).hasSize(1)
            assertThat(tokens.values).containsExactlyInAnyOrder(newToken)
        }
    }
}
