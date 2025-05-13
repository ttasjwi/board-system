package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.exception.ArticleCommentNotFoundException
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-comment-application-service] ArticleCommentReadUseCaseImpl")
class ArticleCommentReadUseCaseImplTest {

    private lateinit var useCase: ArticleCommentReadUseCase
    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articleCommentPersistencePortFixture = container.articleCommentPersistencePortFixture
        useCase = container.articleCommentReadUseCase
    }


    @Test
    @DisplayName("성공 테스트: 부모 댓글이 없을 경우")
    fun testSuccess1() {
        // given
        val articleComment = articleCommentPersistencePortFixture.save(
            articleCommentFixture(
                articleCommentId = 1L,
                rootParentCommentId = 1L,
                parentCommentWriterId = null,
                parentCommentWriterNickname = null,
            )
        )

        // when
        val response = useCase.read(articleComment.articleCommentId)

        // then
        assertThat(response.articleCommentId).isEqualTo(articleComment.articleId.toString())
        assertThat(response.content).isEqualTo(articleComment.content)
        assertThat(response.articleId).isEqualTo(articleComment.articleId.toString())
        assertThat(response.rootParentCommentId).isEqualTo(articleComment.rootParentCommentId.toString())
        assertThat(response.writerId).isEqualTo(articleComment.writerId.toString())
        assertThat(response.writerNickname).isEqualTo(articleComment.writerNickname)
        assertThat(response.parentCommentWriterId).isNull()
        assertThat(response.parentCommentWriterNickname).isNull()
        assertThat(response.createdAt).isEqualTo(articleComment.createdAt.toZonedDateTime())
        assertThat(response.modifiedAt).isEqualTo(articleComment.modifiedAt.toZonedDateTime())
    }

    @Test
    @DisplayName("성공 테스트: 부모 댓글이 있을 경우")
    fun testSuccess2() {
        // given
        val articleComment = articleCommentPersistencePortFixture.save(
            articleCommentFixture(
                articleCommentId = 2L,
                rootParentCommentId = 1L,
                parentCommentWriterId = 12345L,
                parentCommentWriterNickname = "땃고양이",
            )
        )

        // when
        val response = useCase.read(articleComment.articleCommentId)

        // then
        assertThat(response.articleCommentId).isEqualTo(articleComment.articleCommentId.toString())
        assertThat(response.content).isEqualTo(articleComment.content)
        assertThat(response.articleId).isEqualTo(articleComment.articleId.toString())
        assertThat(response.rootParentCommentId).isEqualTo(articleComment.rootParentCommentId.toString())
        assertThat(response.writerId).isEqualTo(articleComment.writerId.toString())
        assertThat(response.writerNickname).isEqualTo(articleComment.writerNickname)
        assertThat(response.parentCommentWriterId).isEqualTo(articleComment.parentCommentWriterId!!.toString())
        assertThat(response.parentCommentWriterNickname).isEqualTo(articleComment.parentCommentWriterNickname!!.toString())
        assertThat(response.createdAt).isEqualTo(articleComment.createdAt.toZonedDateTime())
        assertThat(response.modifiedAt).isEqualTo(articleComment.modifiedAt.toZonedDateTime())
    }

    @Test
    @DisplayName("실패 - 찾지 못 했을 때")
    fun testNotFound() {
        // given
        val commentId = 12134134L

        // when
        val exception = assertThrows<ArticleCommentNotFoundException> {
            useCase.read(commentId)
        }

        // then
        assertThat(exception.args).containsExactly(commentId)
    }

    @Test
    @DisplayName("실패 - 찾았으나 삭제됐을 경우")
    fun testDeleted() {
        // given
        val articleComment = articleCommentPersistencePortFixture.save(
            articleCommentFixture(
                articleCommentId = 12134134L,
                rootParentCommentId = 1L,
                parentCommentWriterId = 12345L,
                parentCommentWriterNickname = "땃고양이",
                deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_WRITER
            )
        )

        // when
        val exception = assertThrows<ArticleCommentNotFoundException> {
            useCase.read(articleComment.articleCommentId)
        }

        // then
        assertThat(exception.args).containsExactly(articleComment.articleCommentId)
    }
}
