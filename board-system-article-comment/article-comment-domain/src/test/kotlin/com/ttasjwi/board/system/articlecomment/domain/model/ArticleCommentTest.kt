package com.ttasjwi.board.system.articlecomment.domain.model

import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleComment 테스트")
class ArticleCommentTest {

    @Test
    @DisplayName("create : 게시글 댓글 생성")
    fun createTest() {
        // given
        val articleCommentId = 1L
        val content = "댓글"
        val articleId = 44L
        val rootParentCommentId = 1L
        val writerId = 555L
        val writerNickname = "땃쥐"
        val targetCommentWriterId = null
        val targetCommentWriterNickname = null
        val createdAt = appDateTimeFixture(minute = 13)

        // when
        val articleComment = ArticleComment.create(
            articleCommentId = articleCommentId,
            content = content,
            articleId = articleId,
            rootParentCommentId = rootParentCommentId,
            writerId = writerId,
            writerNickname = writerNickname,
            targetCommentWriterId = targetCommentWriterId,
            targetCommentWriterNickname = targetCommentWriterNickname,
            createdAt = createdAt,
        )

        // then
        assertThat(articleComment.articleCommentId).isEqualTo(articleCommentId)
        assertThat(articleComment.content).isEqualTo(content)
        assertThat(articleComment.articleId).isEqualTo(articleId)
        assertThat(articleComment.rootParentCommentId).isEqualTo(rootParentCommentId)
        assertThat(articleComment.writerId).isEqualTo(writerId)
        assertThat(articleComment.writerNickname).isEqualTo(writerNickname)
        assertThat(articleComment.targetCommentWriterId).isNull()
        assertThat(articleComment.targetCommentWriterNickname).isNull()
        assertThat(articleComment.deleteStatus).isEqualTo(ArticleCommentDeleteStatus.NOT_DELETED)
        assertThat(articleComment.createdAt).isEqualTo(createdAt)
        assertThat(articleComment.modifiedAt).isEqualTo(createdAt)
    }


    @Test
    @DisplayName("restore : 값으로부터 게시글 댓글을 복원")
    fun testRestore() {
        // given
        val articleCommentId = 123L
        val content = "이것은 댓글입니다."
        val articleId = 44L
        val rootParentCommentId = 154L
        val writerId = 555L
        val writerNickname = "땃쥐"
        val targetCommentWriterId = 554L
        val targetCommentWriterNickname = "땃고양이"
        val deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_WRITER
        val createdAt = appDateTimeFixture(minute = 13).toLocalDateTime()
        val modifiedAt = appDateTimeFixture(minute = 15).toLocalDateTime()

        // when
        val articleComment = ArticleComment.restore(
            articleCommentId = articleCommentId,
            content = content,
            articleId = articleId,
            rootParentCommentId = rootParentCommentId,
            writerId = writerId,
            writerNickname = writerNickname,
            targetCommentWriterId = targetCommentWriterId,
            targetCommentWriterNickname = targetCommentWriterNickname,
            deleteStatus = deleteStatus,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )

        // then
        assertThat(articleComment.articleCommentId).isEqualTo(articleCommentId)
        assertThat(articleComment.content).isEqualTo(content)
        assertThat(articleComment.articleId).isEqualTo(articleId)
        assertThat(articleComment.rootParentCommentId).isEqualTo(rootParentCommentId)
        assertThat(articleComment.writerId).isEqualTo(writerId)
        assertThat(articleComment.writerNickname).isEqualTo(writerNickname)
        assertThat(articleComment.targetCommentWriterId).isEqualTo(targetCommentWriterId)
        assertThat(articleComment.targetCommentWriterNickname).isEqualTo(targetCommentWriterNickname)
        assertThat(articleComment.deleteStatus).isEqualTo(deleteStatus)
        assertThat(articleComment.createdAt).isEqualTo(AppDateTime.from(createdAt))
        assertThat(articleComment.modifiedAt).isEqualTo(AppDateTime.from(modifiedAt))
    }


    @Test
    @DisplayName("deleteByWriter : 작성자에 의한 soft 삭제")
    fun deleteByWriterTest() {
        // given
        val articleComment = articleCommentFixture(
            deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED,
        )

        // when
        articleComment.deleteByWriter()

        // then
        assertThat(articleComment.deleteStatus).isEqualTo(ArticleCommentDeleteStatus.DELETED_BY_WRITER)
    }

    @Test
    @DisplayName("isDeleted : 삭제됐는 지 여부를 반환")
    fun isDeletedTest() {
        // given
        val article1 = articleCommentFixture(
            deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED
        )
        val article2 = articleCommentFixture(
            deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_WRITER
        )
        val article3 = articleCommentFixture(
            deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_MANAGER
        )

        // when
        // then
        assertThat(article1.isDeleted()).isFalse()
        assertThat(article2.isDeleted()).isTrue()
        assertThat(article3.isDeleted()).isTrue()
    }

    @Nested
    @DisplayName("isRootComment: 루트 댓글인지 여부 반환")
    inner class IsRootCommentTest {

        @Test
        @DisplayName("루트부모댓글 Id 값이 자기 자신과 같으면 루트댓글이다.")
        fun test1() {
            // given
            val articleCommentId = 1234L
            val rootParentCommentId = 1234L
            val articleComment = articleCommentFixture(
                articleCommentId = articleCommentId,
                rootParentCommentId = rootParentCommentId,
            )

            // when
            val isRootComment = articleComment.isRootComment()

            // then
            assertThat(isRootComment).isTrue()
        }

        @Test
        @DisplayName("루트부모댓글 Id 값이 자기 자신과 다르면 루트댓글이 아니다.")
        fun test2() {
            // given
            val articleCommentId = 1234L
            val rootParentCommentId = 5432L
            val articleComment = articleCommentFixture(
                articleCommentId = articleCommentId,
                rootParentCommentId = rootParentCommentId,
            )

            // when
            val isRootComment = articleComment.isRootComment()

            // then
            assertThat(isRootComment).isFalse()
        }
    }

    @Test
    @DisplayName("toString : 디버깅용 문자열 반환")
    fun toStringTest() {
        // given
        val articleCommentId = 123L
        val content = "이것은 댓글입니다."
        val articleId = 44L
        val rootParentCommentId = 154L
        val writerId = 555L
        val writerNickname = "땃쥐"
        val targetCommentWriterId = 554L
        val targetCommentWriterNickname = "땃고양이"
        val deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_WRITER
        val createdAt = appDateTimeFixture(minute = 13)
        val modifiedAt = appDateTimeFixture(minute = 15)

        // when
        val articleComment = articleCommentFixture(
            articleCommentId = articleCommentId,
            content = content,
            articleId = articleId,
            rootParentCommentId = rootParentCommentId,
            writerId = writerId,
            writerNickname = writerNickname,
            targetCommentWriterId = targetCommentWriterId,
            targetCommentWriterNickname = targetCommentWriterNickname,
            deleteStatus = deleteStatus,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )
        assertThat(articleComment.toString()).isEqualTo("ArticleComment(articleCommentId=$articleCommentId, articleId=$articleId, rootParentCommentId=$rootParentCommentId, writerId=$writerId, writerNickname='$writerNickname', targetCommentWriterId=$targetCommentWriterId, targetCommentWriterNickname=$targetCommentWriterNickname, createdAt=$createdAt, content='$content', deleteStatus=$deleteStatus, modifiedAt=$modifiedAt)")
    }
}
