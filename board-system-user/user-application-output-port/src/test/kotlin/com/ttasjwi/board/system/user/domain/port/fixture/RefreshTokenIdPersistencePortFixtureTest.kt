package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenIdPersistencePortFixture 테스트")
class RefreshTokenIdPersistencePortFixtureTest {

    private lateinit var refreshTokenIdPersistencePortFixture: RefreshTokenIdPersistencePortFixture

    @BeforeEach
    fun setup() {
        refreshTokenIdPersistencePortFixture = RefreshTokenIdPersistencePortFixture()
    }

    @Test
    @DisplayName("리프레시토큰 아이디 추가, 삭제, 존재여부 확인 테스트")
    fun test() {
        val userId = 2L

        for (tokenId in 1..10) {
            refreshTokenIdPersistencePortFixture.save(userId, tokenId.toLong(), AppDateTime.now().plusHours(1))
        }

        for (tokenId in 1..10) {
            if (tokenId % 2 == 0) {
                refreshTokenIdPersistencePortFixture.remove(userId, tokenId.toLong())
            }
        }


        for (tokenId in 1..10) {
            if (tokenId % 2 == 0) {
                assertThat(refreshTokenIdPersistencePortFixture.exists(userId, tokenId.toLong())).isFalse()
            } else {
                assertThat(refreshTokenIdPersistencePortFixture.exists(userId, tokenId.toLong())).isTrue()
            }
        }
    }
}
