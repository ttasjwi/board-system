package com.ttasjwi.board.system.articleview.domain.port.fixture

import com.ttasjwi.board.system.articleview.domain.model.fixture.articleViewCountFixture
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-view-application-output-port] ArticleViewCountPersistencePortFixture 테스트")
class ArticleViewCountPersistencePortFixtureTest {

    private lateinit var articleViewCountPersistencePortFixture: ArticleViewCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        articleViewCountPersistencePortFixture = ArticleViewCountPersistencePortFixture()
    }


    @Test
    @DisplayName("조회수 증가 후 게시글 Id로 조회 테스트")
    fun increaseAndFindByIdOrNullTest() {
        // given
        val articleId = 123153L
        articleViewCountPersistencePortFixture.increase(articleId)

        // when
        val articleViewCount = articleViewCountPersistencePortFixture.findByIdOrNull(articleId)!!

        // then
        assertThat(articleViewCount.articleId).isEqualTo(articleId)
        assertThat(articleViewCount.viewCount).isEqualTo(1)
    }


    @Test
    @DisplayName("조회수 2회 증가 후 게시글 Id로 조회 테스트")
    fun doubleIncreaseAndFindByIdOrNullTest() {
        // given
        val articleId = 123153L
        articleViewCountPersistencePortFixture.increase(articleId)
        articleViewCountPersistencePortFixture.increase(articleId)

        // when
        val articleViewCount = articleViewCountPersistencePortFixture.findByIdOrNull(articleId)!!

        // then
        assertThat(articleViewCount.articleId).isEqualTo(articleId)
        assertThat(articleViewCount.viewCount).isEqualTo(2)
    }


    @Test
    @DisplayName("조회수가 저장되어 있지않은 게시글 Id로 조회 시 null 반환")
    fun notFoundTest() {
        // given
        val articleId = 12143125L

        // when
        val articleViewCount = articleViewCountPersistencePortFixture.findByIdOrNull(articleId)

        // then
        assertThat(articleViewCount).isNull()
    }
}
