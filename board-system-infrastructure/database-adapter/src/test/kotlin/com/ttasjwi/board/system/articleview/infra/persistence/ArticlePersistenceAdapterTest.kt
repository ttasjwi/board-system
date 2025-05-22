package com.ttasjwi.board.system.articleview.infra.persistence

import com.ttasjwi.board.system.articleview.infra.persistence.jpa.JpaArticle
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-view-database-adapter] ArticlePersistenceAdapter 테스트")
class ArticlePersistenceAdapterTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("existsById : articleId 값으로 게시글 존재여부 확인")
    inner class ExistsByIdTest {

        @Test
        @DisplayName("존재하면 true 반환")
        fun test1() {
            // given
            val article = JpaArticle(
                articleId = 123L,
                articleCategoryId = 13445L,
                title = "제목",
                content = "본문",
                boardId = 1234L,
                writerId = 122L,
                writerNickname = "땃쥐",
                createdAt = appDateTimeFixture().toLocalDateTime(),
                modifiedAt = appDateTimeFixture().toLocalDateTime(),
            )
            entityManager.persist(article)
            flushAndClearEntityManager()

            // when
            val exists = articleViewArticlePersistenceAdapter.existsById(article.articleId)

            // then
            assertThat(exists).isTrue()
        }


        @Test
        @DisplayName("존재하지 않으면 false 반환")
        fun test2() {
            // given
            val articleId = 15555L

            // when
            // then
            val exists = articleViewArticlePersistenceAdapter.existsById(articleId)

            assertThat(exists).isFalse()
        }
    }
}
