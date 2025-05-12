package com.ttasjwi.board.system.articlecomment.domain.model.fixture

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
        assertThat(articleComment.targetCommentWriterId).isNull()
        assertThat(articleComment.targetCommentWriterNickname).isNull()
        assertThat(articleComment.deleted).isFalse()
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
        val targetCommentWriterId = 554L
        val targetCommentWriterNickname = "땃고양이"
        val deleted = true
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
            deleted = deleted,
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
        assertThat(articleComment.deleted).isEqualTo(deleted)
        assertThat(articleComment.createdAt).isEqualTo(createdAt)
        assertThat(articleComment.modifiedAt).isEqualTo(modifiedAt)
    }
}
