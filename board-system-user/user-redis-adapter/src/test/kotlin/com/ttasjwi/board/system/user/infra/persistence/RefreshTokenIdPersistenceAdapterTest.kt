package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.infra.test.MemberRedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenIdPersistenceAdapterTest")
class RefreshTokenIdPersistenceAdapterTest : MemberRedisAdapterTest() {

    @AfterEach
    fun tearDown() {
        val keys = redisTemplate.keys("board-system::member::*::refresh-token::*")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    @Test
    @DisplayName("리프레시토큰 아이디 추가, 삭제, 존재여부 확인 테스트")
    fun test() {
        val memberId = 2L

        for (tokenId in 1..10) {
            refreshTokenIdPersistenceAdapter.save(memberId, tokenId.toLong(), AppDateTime.now().plusHours(1))
        }

        for (tokenId in 1..10) {
            if (tokenId % 2 == 0) {
                refreshTokenIdPersistenceAdapter.remove(memberId, tokenId.toLong())
            }
        }


        for (tokenId in 1..10) {
            if (tokenId % 2 == 0) {
                assertThat(refreshTokenIdPersistenceAdapter.exists(memberId, tokenId.toLong())).isFalse()
            } else {
                assertThat(refreshTokenIdPersistenceAdapter.exists(memberId, tokenId.toLong())).isTrue()
            }
        }
    }
}
