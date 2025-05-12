package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentPersistencePort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-application-output-port] ArticleCommentPersistencePortFixture 테스트")
class ArticleCommentPersistencePortFixtureTest {

    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePort

    @BeforeEach
    fun setUp() {
        articleCommentPersistencePortFixture = ArticleCommentPersistencePortFixture()
    }

    @Nested
    @DisplayName("저장 후 식별자 조회 테스트")
    inner class SaveAndFindTest {


        @Test
        @DisplayName("조회 성공하는 경우")
        fun testSuccess() {
            // given
            val articleComment = articleCommentFixture()

            // when
            articleCommentPersistencePortFixture.save(articleComment)
            val findArticleComment = articleCommentPersistencePortFixture.findById(articleComment.articleCommentId)!!

            // then
            assertThat(findArticleComment.articleCommentId).isEqualTo(articleComment.articleCommentId)
            assertThat(findArticleComment.content).isEqualTo(articleComment.content)
            assertThat(findArticleComment.articleId).isEqualTo(articleComment.articleId)
            assertThat(findArticleComment.rootParentCommentId).isEqualTo(articleComment.rootParentCommentId)
            assertThat(findArticleComment.writerId).isEqualTo(articleComment.writerId)
            assertThat(findArticleComment.writerNickname).isEqualTo(articleComment.writerNickname)
            assertThat(findArticleComment.targetCommentWriterId).isEqualTo(articleComment.targetCommentWriterId)
            assertThat(findArticleComment.targetCommentWriterNickname).isEqualTo(articleComment.targetCommentWriterNickname)
            assertThat(findArticleComment.deleteStatus).isEqualTo(articleComment.deleteStatus)
            assertThat(findArticleComment.createdAt).isEqualTo(articleComment.createdAt)
            assertThat(findArticleComment.modifiedAt).isEqualTo(articleComment.modifiedAt)
        }


        @Test
        @DisplayName("조회 실패 시 null 반환")
        fun testNotFound() {
            // given
            // when
            val findArticleComment = articleCommentPersistencePortFixture.findById(14555665L)

            // then
            assertThat(findArticleComment).isNull()
        }
    }
}
