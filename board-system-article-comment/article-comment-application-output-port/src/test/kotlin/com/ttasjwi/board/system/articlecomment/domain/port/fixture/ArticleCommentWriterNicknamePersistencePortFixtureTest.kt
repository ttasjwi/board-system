package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-application-output-port] ArticleCommentWriterNicknamePersistencePortFixture 테스트")
class ArticleCommentWriterNicknamePersistencePortFixtureTest {

    private lateinit var articleCommentWriterNicknamePersistencePortFixture: ArticleCommentWriterNicknamePersistencePortFixture

    @BeforeEach
    fun setup() {
        articleCommentWriterNicknamePersistencePortFixture = ArticleCommentWriterNicknamePersistencePortFixture()
    }

    @Nested
    @DisplayName("find : 저장 후 조회 테스트")
    inner class FindTest {

        @Test
        @DisplayName("저장된 것이 잘 조회되는 지 테스트")
        fun testCreate() {
            // given
            val commentWriterId = 1567L
            val commentWriterNickname = "땃쥐"

            // when
            articleCommentWriterNicknamePersistencePortFixture.save(
                commentWriterId = commentWriterId,
                commentWriterNickname = commentWriterNickname
            )
            val findCommentWriterNickname = articleCommentWriterNicknamePersistencePortFixture.findCommentWriterNickname(commentWriterId = commentWriterId)!!

            // then
            assertThat(findCommentWriterNickname).isEqualTo(commentWriterNickname)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findCommentWriterNickname = articleCommentWriterNicknamePersistencePortFixture.findCommentWriterNickname(commentWriterId = 128888L)
            assertThat(findCommentWriterNickname).isNull()
        }
    }
}
