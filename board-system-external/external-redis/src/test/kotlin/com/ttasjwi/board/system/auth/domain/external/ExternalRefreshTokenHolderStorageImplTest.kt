package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.RedisTest
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

@DisplayName("ExternalRefreshTokenHolderStorageImpl 테스트")
class ExternalRefreshTokenHolderStorageImplTest : RedisTest() {

    companion object {
        private val memberId = memberIdFixture(1557L)
    }

    @AfterEach
    fun tearDown() {
        externalRefreshTokenHolderStorage.removeByMemberId(memberId)
    }

    @Nested
    @DisplayName("append: 리프레시토큰 홀더를 새로 저장한다.")
    inner class Append {

        @Test
        @DisplayName("동작 테스트")
        fun testSuccess() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = memberId.value,
                refreshTokenId = "abc",
            )
            val refreshTokenHolder = refreshTokenHolderFixture(
                memberId = memberId.value,
                tokens = mutableMapOf(refreshToken.refreshTokenId to refreshToken)
            )
            val expiresAt = ZonedDateTime.now().plusMinutes(30)

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
            val refreshToken = refreshTokenFixture(
                memberId = memberId.value,
                refreshTokenId = "abc",
            )
            val savedRefreshTokenHolder = refreshTokenHolderFixture(
                memberId = memberId.value,
                tokens = mutableMapOf(refreshToken.refreshTokenId to refreshToken)
            )
            val expiresAt = ZonedDateTime.now().plusMinutes(30)
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
            val findRefreshTokenHolder = externalRefreshTokenHolderStorage.findByMemberIdOrNull(memberIdFixture(56644))

            assertThat(findRefreshTokenHolder).isNull()
        }
    }

    @Nested
    @DisplayName("removeByMemberId: MemberId로 리프레시토큰 홀더를 제거한다")
    inner class RemoveByMemberId {


        @Test
        @DisplayName("제거 후 같은 memberId로 찾으면 null 이 반환된다")
        fun testRemove() {
            // given
            val refreshTokenHolder = refreshTokenHolderFixture(memberId = memberId.value)

            externalRefreshTokenHolderStorage.append(memberId, refreshTokenHolder, ZonedDateTime.now().plusMinutes(30))

            // when
            externalRefreshTokenHolderStorage.removeByMemberId(memberId)

            // then
            val findRefreshTokenHolder = externalRefreshTokenHolderStorage.findByMemberIdOrNull(memberId)
            assertThat(findRefreshTokenHolder).isNull()
        }
    }
}
