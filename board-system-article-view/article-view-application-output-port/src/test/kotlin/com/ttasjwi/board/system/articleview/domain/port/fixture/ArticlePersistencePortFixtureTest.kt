package com.ttasjwi.board.system.articleview.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-view-application-output-port] ArticlePersistencePortFixture 테스트")
class ArticlePersistencePortFixtureTest {

    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setup() {
        articlePersistencePortFixture = ArticlePersistencePortFixture()
    }

    @Nested
    @DisplayName("existsById : 게시글 존재여부 확인")
    inner class ExistsByIdTest {


        @Test
        @DisplayName("저장된 게시글이 있을 경우 true 반환")
        fun test1() {
            // given
            val articleId = 2345L
            articlePersistencePortFixture.save(articleId)

            // when
            val exists = articlePersistencePortFixture.existsById(articleId)

            // then
            assertThat(exists).isTrue
        }

        @Test
        @DisplayName("저장된 게시글이 없을 경우 false 반환")
        fun test2() {
            // given
            val articleId = 5553155L

            // when
            val exists = articlePersistencePortFixture.existsById(articleId)

            // then
            assertThat(exists).isFalse
        }
    }
}
