package com.ttasjwi.board.system.articleread.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-domain] ArticleSummaryQueryModelFixture 테스트")
class ArticleSummaryQueryModelFixtureTest {


    @Test
    @DisplayName("디폴트 파라미터 형태로 잘 생성되는 지 테스트")
    fun test1() {
        // given
        // when
        val articleSummary = articleSummaryQueryModelFixture()

        // then
        assertThat(articleSummary).isNotNull
    }


    @Test
    @DisplayName("커스텀 파라미터 테스트")
    fun test2() {
        // given
        val articleId = 41L
        val title = "우리 늘봄이 귀엽죠"
        val articleCategoryId = 25L
        val articleCategoryName = "사진"
        val articleCategorySlug = "picture"
        val boardId = 4L
        val boardName = "고양이"
        val boardSlug = "cat"
        val writerId = 9L
        val writerNickname = "땃쥐"
        val commentCount = 7L
        val likeCount = 5L
        val dislikeCount = 0L
        val createdAt= appDateTimeFixture(minute = 30)

        // when
        val articleSummary = articleSummaryQueryModelFixture(
            articleId = articleId,
            title = title,
            articleCategoryId = articleCategoryId,
            articleCategoryName = articleCategoryName,
            articleCategorySlug = articleCategorySlug,
            boardId = boardId,
            boardName = boardName,
            boardSlug = boardSlug,
            writerId = writerId,
            writerNickname = writerNickname,
            commentCount = commentCount,
            likeCount = likeCount,
            dislikeCount = dislikeCount,
            createdAt = createdAt,
        )

        // then
        assertThat(articleSummary.articleId).isEqualTo(articleId)
        assertThat(articleSummary.title).isEqualTo(title)
        assertThat(articleSummary.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(articleSummary.articleCategoryName).isEqualTo(articleCategoryName)
        assertThat(articleSummary.articleCategorySlug).isEqualTo(articleCategorySlug)
        assertThat(articleSummary.boardId).isEqualTo(boardId)
        assertThat(articleSummary.boardName).isEqualTo(boardName)
        assertThat(articleSummary.boardSlug).isEqualTo(boardSlug)
        assertThat(articleSummary.writerId).isEqualTo(writerId)
        assertThat(articleSummary.writerNickname).isEqualTo(writerNickname)
        assertThat(articleSummary.commentCount).isEqualTo(commentCount)
        assertThat(articleSummary.likeCount).isEqualTo(likeCount)
        assertThat(articleSummary.dislikeCount).isEqualTo(dislikeCount)
        assertThat(articleSummary.createdAt).isEqualTo(createdAt)
    }
}
