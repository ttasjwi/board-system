package com.ttasjwi.board.system.articleview.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Duration

@DisplayName("[article-view-application-output-port] ArticleViewCountLockPersistencePortFixture 테스트")
class ArticleViewCountLockPersistencePortFixtureTest {

    private lateinit var articleViewCountLockPersistencePortFixture: ArticleViewCountLockPersistencePortFixture

    @BeforeEach
    fun setup() {
        articleViewCountLockPersistencePortFixture = ArticleViewCountLockPersistencePortFixture()
    }


    @Test
    @DisplayName("LOCK_USER_ID 에 대해서는 락 획득에 실패")
    fun test1() {
        // given
        val articleId = 1234L
        val lockUserId = ArticleViewCountLockPersistencePortFixture.LOCK_USER_ID

        // when
        val getLockSuccess =
            articleViewCountLockPersistencePortFixture.lock(articleId, lockUserId, Duration.ofMinutes(10))

        // then
        assertThat(getLockSuccess).isFalse()
    }

    @Test
    @DisplayName("LOCK_USER_ID 가 아닐 경우 락 획득에 성공")
    fun test2() {
        // given
        val articleId = 1234L
        val userId = 21324134L

        // when
        val getLockSuccess = articleViewCountLockPersistencePortFixture.lock(articleId, userId, Duration.ofMinutes(10))

        // then
        assertThat(getLockSuccess).isTrue()
    }
}
