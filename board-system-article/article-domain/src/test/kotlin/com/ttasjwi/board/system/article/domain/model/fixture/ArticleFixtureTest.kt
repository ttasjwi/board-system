package com.ttasjwi.board.system.article.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] ArticleFixture: 게시글 픽스쳐 테스트")
class ArticleFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가진다.")
    fun test1() {
        val article = articleFixture()

        assertThat(article.articleId).isNotNull()
        assertThat(article.title).isNotNull()
        assertThat(article.content).isNotNull()
        assertThat(article.boardId).isNotNull()
        assertThat(article.articleCategoryId).isNotNull()
        assertThat(article.writerId).isNotNull()
        assertThat(article.writerNickname).isNotNull()
        assertThat(article.createdAt).isNotNull()
        assertThat(article.modifiedAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val articleId = 14578L
        val title = "title"
        val content = "content"
        val boardId = 1234556L
        val articleCategoryId = 2314558888L
        val writerId = 334L
        val writerNickname = "땃고양이"
        val createdAt = appDateTimeFixture(minute = 13)
        val modifiedAt = appDateTimeFixture(minute = 43)

        val article = articleFixture(
            articleId = articleId,
            title = title,
            content = content,
            boardId = boardId,
            articleCategoryId = articleCategoryId,
            writerId = writerId,
            writerNickname = writerNickname,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )

        assertThat(article.articleId).isEqualTo(articleId)
        assertThat(article.title).isEqualTo(title)
        assertThat(article.content).isEqualTo(content)
        assertThat(article.boardId).isEqualTo(boardId)
        assertThat(article.articleCategoryId).isEqualTo(articleCategoryId)
        assertThat(article.writerId).isEqualTo(writerId)
        assertThat(article.writerNickname).isEqualTo(writerNickname)
        assertThat(article.createdAt).isEqualTo(createdAt)
        assertThat(article.modifiedAt).isEqualTo(modifiedAt)
    }
}
