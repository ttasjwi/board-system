package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.user.infra.test.MemberRedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MemberRefreshTokenIdListPersistenceAdapter 테스트")
class UserRefreshTokenIdListPersistenceAdapterTest : MemberRedisAdapterTest() {

    @AfterEach
    fun tearDown() {
        val keys = redisTemplate.keys("board-system::member::*::refresh-token-ids")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    @Test
    @DisplayName("리프레시토큰 아이디 값이 큰 것들이 지정한 갯수만큼 유지된다.")
    fun saveTest() {
        val memberId = 1L

        for (id in 1..5) {
            memberRefreshTokenIdListPersistenceAdapter.add(
                memberId = memberId,
                refreshTokenId = id.toLong(),
                limit = 3,
            )
        }
        val tokenIds = memberRefreshTokenIdListPersistenceAdapter.findAll(memberId)
        assertThat(tokenIds).hasSize(3)
        assertThat(tokenIds).containsExactlyInAnyOrder(3, 4, 5)
    }


    @Test
    @DisplayName("삭제 / 존재여부 확인 기능 테스트")
    fun removeAndExistsTest() {
        val memberId = 1L

        for (id in 1..10) {
            memberRefreshTokenIdListPersistenceAdapter.add(
                memberId = memberId,
                refreshTokenId = id.toLong(),
                limit = 10,
            )
        }

        for (id in 1..10 step 2) {
            memberRefreshTokenIdListPersistenceAdapter.remove(memberId, id.toLong())
        }

        val tokenIds = memberRefreshTokenIdListPersistenceAdapter.findAll(memberId)
        assertThat(tokenIds).hasSize(5)
        assertThat(tokenIds).containsExactlyInAnyOrder(2, 4, 6, 8, 10)

        for (id in 1..10) {
            if (id%2 == 0) {
                assertThat(memberRefreshTokenIdListPersistenceAdapter.exists(memberId, id.toLong())).isTrue()
            } else {
                assertThat(memberRefreshTokenIdListPersistenceAdapter.exists(memberId, id.toLong())).isFalse()
            }
        }
    }
}

