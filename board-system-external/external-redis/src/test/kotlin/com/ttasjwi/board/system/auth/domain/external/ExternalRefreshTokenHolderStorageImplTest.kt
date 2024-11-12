package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("ExternalRefreshTokenHolderStorageImpl 테스트")
class ExternalRefreshTokenHolderStorageImplTest {

    private lateinit var externalRefreshTokenHolderStorage: ExternalRefreshTokenHolderStorageImpl

    @BeforeEach
    fun setup() {
        externalRefreshTokenHolderStorage = ExternalRefreshTokenHolderStorageImpl()
    }

    @Nested
    @DisplayName("append: 리프레시토큰 홀더를 새로 저장한다.")
    inner class Append {

        @Test
        @DisplayName("동작 테스트")
        fun testSuccess() {
            // given
            val memberId = memberIdFixture(130L)
            val refreshToken = refreshTokenFixture(
                memberId = memberId.value,
                refreshTokenId = "abc",
            )
            val refreshTokenHolder = refreshTokenHolderFixture(
                memberId = memberId.value,
                tokens = mutableMapOf(refreshToken.refreshTokenId to refreshToken)
            )
            val expiresAt = timeFixture(minute = 13)

            // when
            // then
            externalRefreshTokenHolderStorage.append(memberId, refreshTokenHolder, expiresAt)
        }
    }

    @Nested
    @DisplayName("findByMemberIdOrNull: 회원 아이디로 리프레시토큰 홀더를 조회한다")
    inner class FindByMemberIdOrNull {

        @Test
        @DisplayName("append 후 find 했을 때 잘 찾아지는 지 테스트")
        fun testSuccess() {
            // given
            val memberId = memberIdFixture(130L)
            val refreshToken = refreshTokenFixture(
                memberId = memberId.value,
                refreshTokenId = "abc",
            )
            val savedRefreshTokenHolder = refreshTokenHolderFixture(
                memberId = memberId.value,
                tokens = mutableMapOf(refreshToken.refreshTokenId to refreshToken)
            )
            val expiresAt = timeFixture(minute = 13)
            externalRefreshTokenHolderStorage.append(memberId, savedRefreshTokenHolder, expiresAt)

            // when
            val findRefreshTokenHolder = externalRefreshTokenHolderStorage.findByMemberIdOrNull(memberId)!!

            // then
            val tokens = findRefreshTokenHolder.getTokens()

            assertThat(findRefreshTokenHolder.authMember).isEqualTo(savedRefreshTokenHolder.authMember)
            assertThat(tokens.size).isEqualTo(1)
            assertThat(tokens).containsAllEntriesOf(savedRefreshTokenHolder.getTokens())
        }

        @Test
        @DisplayName("없는 리프레시토큰 홀더 조회 시 null 반환")
        fun testNull() {
            val findRefreshTokenHolder = externalRefreshTokenHolderStorage.findByMemberIdOrNull(memberIdFixture(2L))

            assertThat(findRefreshTokenHolder).isNull()
        }
    }
}
