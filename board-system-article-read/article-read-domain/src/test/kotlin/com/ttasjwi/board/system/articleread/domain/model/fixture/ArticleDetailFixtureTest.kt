package com.ttasjwi.board.system.articleread.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-domain] ArticleDetailFixture 테스트")
class ArticleDetailFixtureTest {


    @Test
    @DisplayName("디폴트 파라미터 형태로 잘 생성되는 지 테스트")
    fun test1() {
        // given
        // when
        val articleDetail = articleDetailFixture()

        // then
        assertThat(articleDetail).isNotNull
    }


    @Test
    @DisplayName("커스텀 파라미터 테스트")
    fun test2() {
        // given
        val articleId = 41L
        val title = "우리 늘봄이 귀엽죠"
        val content = "너무 귀여워 늘봄이"
        val articleCategoryId = 25L
        val articleCategoryName = "사진"
        val articleCategorySlug = "picture"
        val articleCategoryAllowWrite = true
        val articleCategoryAllowSelfEditDelete = true
        val articleCategoryAllowComment = true
        val articleCategoryAllowLike = true
        val articleCategoryAllowDislike = false
        val boardId = 4L
        val boardName = "고양이"
        val boardSlug = "cat"
        val writerId = 9L
        val writerNickname = "땃쥐"
        val commentCount = 7L
        val liked = true
        val likeCount = 5L
        val disliked = false
        val dislikeCount = 0L
        val createdAt= appDateTimeFixture(minute = 30)
        val modifiedAt = appDateTimeFixture(minute = 30)

        // when
        val articleDetail = articleDetailFixture(
            articleId = articleId,
            title = title,
            content = content,
            articleCategoryId = articleCategoryId,
            articleCategoryName = articleCategoryName,
            articleCategorySlug = articleCategorySlug,
            articleCategoryAllowWrite = articleCategoryAllowWrite,
            articleCategoryAllowSelfEditDelete = articleCategoryAllowSelfEditDelete,
            articleCategoryAllowComment = articleCategoryAllowComment,
            articleCategoryAllowLike = articleCategoryAllowLike,
            articleCategoryAllowDislike = articleCategoryAllowDislike,
            boardId = boardId,
            boardName = boardName,
            boardSlug = boardSlug,
            writerId = writerId,
            writerNickname = writerNickname,
            commentCount = commentCount,
            liked = liked,
            likeCount = likeCount,
            disliked = disliked,
            dislikeCount = dislikeCount,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )

        // then
        assertThat(articleDetail.articleId).isEqualTo(articleId)
        assertThat(articleDetail.title).isEqualTo(title)
        assertThat(articleDetail.content).isEqualTo(content)
        assertThat(articleDetail.articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleDetail.articleCategory.name).isEqualTo(articleCategoryName)
        assertThat(articleDetail.articleCategory.slug).isEqualTo(articleCategorySlug)
        assertThat(articleDetail.articleCategory.allowWrite).isEqualTo(articleCategoryAllowWrite)
        assertThat(articleDetail.articleCategory.allowSelfEditDelete).isEqualTo(articleCategoryAllowSelfEditDelete)
        assertThat(articleDetail.articleCategory.allowComment).isEqualTo(articleCategoryAllowComment)
        assertThat(articleDetail.articleCategory.allowLike).isEqualTo(articleCategoryAllowLike)
        assertThat(articleDetail.articleCategory.allowDislike).isEqualTo(articleCategoryAllowDislike)
        assertThat(articleDetail.board.boardId).isEqualTo(boardId)
        assertThat(articleDetail.board.name).isEqualTo(boardName)
        assertThat(articleDetail.board.slug).isEqualTo(boardSlug)
        assertThat(articleDetail.writer.writerId).isEqualTo(writerId)
        assertThat(articleDetail.writer.nickname).isEqualTo(writerNickname)
        assertThat(articleDetail.commentCount).isEqualTo(commentCount)
        assertThat(articleDetail.liked).isEqualTo(liked)
        assertThat(articleDetail.likeCount).isEqualTo(likeCount)
        assertThat(articleDetail.disliked).isEqualTo(disliked)
        assertThat(articleDetail.dislikeCount).isEqualTo(dislikeCount)
        assertThat(articleDetail.createdAt).isEqualTo(createdAt)
        assertThat(articleDetail.modifiedAt).isEqualTo(modifiedAt)
    }
}
