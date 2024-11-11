package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.external.fixture.ExternalRefreshTokenHolderStorageFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenHolderStorage (Appender, Finder) 구현체 테스트")
class RefreshTokenHolderStorageImplTest {

    private lateinit var refreshTokenHolderStorage: RefreshTokenHolderStorageImpl

    @BeforeEach
    fun setup() {
        val externalRefreshTokenHolderStorageFixture = ExternalRefreshTokenHolderStorageFixture()
        refreshTokenHolderStorage = RefreshTokenHolderStorageImpl(
            externalRefreshTokenHolderAppender = externalRefreshTokenHolderStorageFixture,
            externalRefreshTokenHolderFinder = externalRefreshTokenHolderStorageFixture
        )
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
            val currenTime = timeFixture(minute = 13)

            // when
            // then
            refreshTokenHolderStorage.append(memberId, refreshTokenHolder, currenTime)
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
            val currenTime = timeFixture(minute = 13)
            refreshTokenHolderStorage.append(memberId, savedRefreshTokenHolder, currenTime)

            // when
            val findRefreshTokenHolder = refreshTokenHolderStorage.findByMemberIdOrNull(memberId)!!

            // then
            val tokens = findRefreshTokenHolder.getTokens()

            assertThat(findRefreshTokenHolder.authMember).isEqualTo(savedRefreshTokenHolder.authMember)
            assertThat(tokens.size).isEqualTo(1)
            assertThat(tokens).containsAllEntriesOf(savedRefreshTokenHolder.getTokens())
        }

        @Test
        @DisplayName("없는 리프레시토큰 홀더 조회 시 null 반환")
        fun testNull() {
            val findRefreshTokenHolder = refreshTokenHolderStorage.findByMemberIdOrNull(memberIdFixture(2L))

            assertThat(findRefreshTokenHolder).isNull()
        }
    }
}
