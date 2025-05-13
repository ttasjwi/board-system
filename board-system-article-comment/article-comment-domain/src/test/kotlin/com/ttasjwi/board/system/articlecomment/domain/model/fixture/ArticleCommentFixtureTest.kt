package com.ttasjwi.board.system.articlecomment.domain.model.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCommentFixture 테스트")
class ArticleCommentFixtureTest {

    @Test
    @DisplayName("기본값 생성 테스트")
    fun test1() {
        // given
        // when
        val articleComment = articleCommentFixture()

        // then
        assertThat(articleComment.articleCommentId).isNotNull()
        assertThat(articleComment.content).isNotNull()
        assertThat(articleComment.articleId).isNotNull()
        assertThat(articleComment.rootParentCommentId).isEqualTo(articleComment.articleCommentId)
        assertThat(articleComment.writerId).isNotNull()
        assertThat(articleComment.writerNickname).isNotNull()
        assertThat(articleComment.parentCommentWriterId).isNull()
        assertThat(articleComment.parentCommentWriterNickname).isNull()
        assertThat(articleComment.deleteStatus).isEqualTo(ArticleCommentDeleteStatus.NOT_DELETED)
        assertThat(articleComment.createdAt).isNotNull()
        assertThat(articleComment.modifiedAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀 값 지정 생성 테스트")
    fun test2() {
        // given
        val articleCommentId = 123L
        val content = "이것은 댓글입니다."
        val articleId = 44L
        val rootParentCommentId = 154L
        val writerId = 555L
        val writerNickname = "땃쥐"
        val parentCommentWriterId = 554L
        val parentCommentWriterNickname = "땃고양이"
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
            parentCommentWriterId = parentCommentWriterId,
            parentCommentWriterNickname = parentCommentWriterNickname,
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
        assertThat(articleComment.parentCommentWriterId).isEqualTo(parentCommentWriterId)
        assertThat(articleComment.parentCommentWriterNickname).isEqualTo(parentCommentWriterNickname)
        assertThat(articleComment.deleteStatus).isEqualTo(deleteStatus)
        assertThat(articleComment.createdAt).isEqualTo(createdAt)
        assertThat(articleComment.modifiedAt).isEqualTo(modifiedAt)
    }
}
