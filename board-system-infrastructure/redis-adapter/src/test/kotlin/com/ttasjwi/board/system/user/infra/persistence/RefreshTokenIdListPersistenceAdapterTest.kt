package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.test.RedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UserRefreshTokenIdListPersistenceAdapter 테스트")
class RefreshTokenIdListPersistenceAdapterTest : RedisAdapterTest() {

    @AfterEach
    fun tearDown() {
        val keys = redisTemplate.keys("board-system::user::*::refresh-token-ids")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    @Test
    @DisplayName("리프레시토큰 아이디 값이 큰 것들이 지정한 갯수만큼 유지된다.")
    fun saveTest() {
        val userId = 1L

        for (id in 1..5) {
            userRefreshTokenIdListPersistenceAdapter.add(
                userId = userId,
                refreshTokenId = id.toLong(),
                limit = 3,
            )
        }
        val tokenIds = userRefreshTokenIdListPersistenceAdapter.findAll(userId)
        assertThat(tokenIds).hasSize(3)
        assertThat(tokenIds).containsExactlyInAnyOrder(3, 4, 5)
    }


    @Test
    @DisplayName("삭제 / 존재여부 확인 기능 테스트")
    fun removeAndExistsTest() {
        val userId = 1L

        for (id in 1..10) {
            userRefreshTokenIdListPersistenceAdapter.add(
                userId = userId,
                refreshTokenId = id.toLong(),
                limit = 10,
            )
        }

        for (id in 1..10 step 2) {
            userRefreshTokenIdListPersistenceAdapter.remove(userId, id.toLong())
        }

        val tokenIds = userRefreshTokenIdListPersistenceAdapter.findAll(userId)
        assertThat(tokenIds).hasSize(5)
        assertThat(tokenIds).containsExactlyInAnyOrder(2, 4, 6, 8, 10)

        for (id in 1..10) {
            if (id%2 == 0) {
                assertThat(userRefreshTokenIdListPersistenceAdapter.exists(userId, id.toLong())).isTrue()
            } else {
                assertThat(userRefreshTokenIdListPersistenceAdapter.exists(userId, id.toLong())).isFalse()
            }
        }
    }
}

