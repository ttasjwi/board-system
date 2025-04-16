package com.ttasjwi.board.system.user.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UserRefreshTokenIdListPersistencePortFixtureTest {

    private lateinit var userRefreshTokenIdListPersistencePortFixture: UserRefreshTokenIdListPersistencePortFixture

    @BeforeEach
    fun setup() {
        userRefreshTokenIdListPersistencePortFixture = UserRefreshTokenIdListPersistencePortFixture()
    }

    @Nested
    @DisplayName("Add : 최대 limit 건까지 유지하여 저장한다.")
    inner class AddTest {

        @Test
        @DisplayName("단건 추가 후 조회 테스트")
        fun test1() {
            val userId = 1L
            val refreshTokenId = 1234L
            userRefreshTokenIdListPersistencePortFixture.add(
                userId = userId,
                refreshTokenId = refreshTokenId,
                limit = 1,
            )

            val findRefreshTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(userId)

            assertThat(findRefreshTokenIds).hasSize(1)
            assertThat(findRefreshTokenIds[0]).isEqualTo(refreshTokenId)
        }


        @Test
        @DisplayName("limit 이하까지 추가했을 때, limit 이하건 내에서는 그대로 유지된다.")
        fun test2() {
            val userId = 1L

            for (id in 1..5) {
                userRefreshTokenIdListPersistencePortFixture.add(
                    userId = userId,
                    refreshTokenId = id.toLong(),
                    limit = 5,
                )
            }

            val findRefreshTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(userId)

            assertThat(findRefreshTokenIds).hasSize(5)
            assertThat(findRefreshTokenIds).containsExactlyInAnyOrder(1, 2, 3, 4, 5)
        }

        @Test
        @DisplayName("limit 를 초과할 경우, 최신의(아이디순) limit 건만 유지된다.")
        fun test3() {
            val userId = 1L

            for (id in 1..5) {
                userRefreshTokenIdListPersistencePortFixture.add(
                    userId = userId,
                    refreshTokenId = id.toLong(),
                    limit = 3,
                )
            }
            val tokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(userId)
            assertThat(tokenIds).hasSize(3)
            assertThat(tokenIds).containsExactlyInAnyOrder(3, 4, 5)
        }

        @Test
        @DisplayName("저장한 RefreshTokenId 가 없으면 빈 리스트가 반환된다.")
        fun test4() {
            val userId = 1L
            val tokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(userId)
            assertThat(tokenIds).hasSize(0)
        }
    }

    @Nested
    @DisplayName("Remove: 사용자의 특정 리프레시토큰Id 를 제거한다.")
    inner class RemoveTest {

        @Test
        @DisplayName("사용자가 가진 토큰목록이 없는 경우 아무 것도 하지 않음.")
        fun test1() {
            val userId = 1L
            val refreshTokenId = 1234L

            userRefreshTokenIdListPersistencePortFixture.remove(userId, refreshTokenId)

            val findRefreshTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(userId)

            assertThat(findRefreshTokenIds).isEmpty()
        }

        @Test
        @DisplayName("사용자가 가진 토큰목록에서 해당 토큰을 제거한다.")
        fun test2() {
            val userId = 1L
            userRefreshTokenIdListPersistencePortFixture.add(
                userId = userId,
                refreshTokenId = 1,
                limit = 3,
            )

            userRefreshTokenIdListPersistencePortFixture.add(
                userId = userId,
                refreshTokenId = 2,
                limit = 3,
            )

            userRefreshTokenIdListPersistencePortFixture.add(
                userId = userId,
                refreshTokenId = 3,
                limit = 3,
            )

            userRefreshTokenIdListPersistencePortFixture.remove(userId, 2L)

            val findRefreshTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(userId)

            assertThat(findRefreshTokenIds).containsExactlyInAnyOrder(1L, 3L)
        }
    }

    @Nested
    @DisplayName("exists : 사용자의 특정 토큰 존재여부 확인")
    inner class ExistsTest {

        @Test
        @DisplayName("사용자가 가진 토큰목록 자체가 없는 경우 false 반환")
        fun test1() {
            val userId = 1L
            val refreshTokenId = 1234L

            val isExist = userRefreshTokenIdListPersistencePortFixture.exists(userId, refreshTokenId)

            assertThat(isExist).isFalse()
        }

        @Test
        @DisplayName("사용자가 가진 토큰목록이 있으면 토큰의 존재여부를 확인하여 반환한다.")
        fun test2() {
            val userId = 1L
            userRefreshTokenIdListPersistencePortFixture.add(
                userId = userId,
                refreshTokenId = 1,
                limit = 3,
            )

            val isExist1 = userRefreshTokenIdListPersistencePortFixture.exists(userId, 1L)
            val isExist2 = userRefreshTokenIdListPersistencePortFixture.exists(userId, 2L)

            assertThat(isExist1).isTrue()
            assertThat(isExist2).isFalse()
        }
    }
}
