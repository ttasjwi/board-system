package com.ttasjwi.board.system.articleread.domain.port.fixture

import com.ttasjwi.board.system.articleread.domain.model.fixture.articleDetailFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-application-output-port] ArticleDetailStorageFixture 테스트")
class ArticleDetailStorageFixtureTest {

    private lateinit var articleDetailStorageFixture: ArticleDetailStorageFixture

    @BeforeEach
    fun setup() {
        articleDetailStorageFixture = ArticleDetailStorageFixture()
    }

    @Test
    @DisplayName("조회 성공 테스트")
    fun testSuccess() {
        // given
        val articleId = 41L
        val title = "우리 늘봄이 귀엽죠"
        val content = "너무 귀여워 늘봄이"
        val articleCategoryId = 25L
        val articleCategoryName = "사진"
        val articleCategorySlug = "picture"
        val articleCategoryAllowSelfDelete = true
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
        val savedArticleDetail = articleDetailFixture(
            articleId = articleId,
            title = title,
            content = content,
            articleCategoryId = articleCategoryId,
            articleCategoryName = articleCategoryName,
            articleCategorySlug = articleCategorySlug,
            articleCategoryAllowSelfDelete = articleCategoryAllowSelfDelete,
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
        articleDetailStorageFixture.save(savedArticleDetail)

        // when
        val findArticleDetail = articleDetailStorageFixture.read(savedArticleDetail.articleId, 456L)!!

        // then
        assertThat(findArticleDetail.articleId).isEqualTo(articleId)
        assertThat(findArticleDetail.title).isEqualTo(title)
        assertThat(findArticleDetail.content).isEqualTo(content)
        assertThat(findArticleDetail.articleCategory.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(findArticleDetail.articleCategory.name).isEqualTo(articleCategoryName)
        assertThat(findArticleDetail.articleCategory.slug).isEqualTo(articleCategorySlug)
        assertThat(findArticleDetail.articleCategory.allowSelfDelete).isEqualTo(articleCategoryAllowSelfDelete)
        assertThat(findArticleDetail.articleCategory.allowLike).isEqualTo(articleCategoryAllowLike)
        assertThat(findArticleDetail.articleCategory.allowDislike).isEqualTo(articleCategoryAllowDislike)
        assertThat(findArticleDetail.board.boardId).isEqualTo(boardId)
        assertThat(findArticleDetail.board.name).isEqualTo(boardName)
        assertThat(findArticleDetail.board.slug).isEqualTo(boardSlug)
        assertThat(findArticleDetail.writer.writerId).isEqualTo(writerId)
        assertThat(findArticleDetail.writer.nickname).isEqualTo(writerNickname)
        assertThat(findArticleDetail.commentCount).isEqualTo(commentCount)
        assertThat(findArticleDetail.liked).isEqualTo(liked)
        assertThat(findArticleDetail.likeCount).isEqualTo(likeCount)
        assertThat(findArticleDetail.disliked).isEqualTo(disliked)
        assertThat(findArticleDetail.dislikeCount).isEqualTo(dislikeCount)
        assertThat(findArticleDetail.createdAt).isEqualTo(createdAt)
        assertThat(findArticleDetail.modifiedAt).isEqualTo(modifiedAt)
    }


    @Test
    @DisplayName("조회 실패 테스트")
    fun testNotFound() {
        val findArticleDetail = articleDetailStorageFixture.read(85747L, 5416L)
        assertThat(findArticleDetail).isNull()
    }
}
