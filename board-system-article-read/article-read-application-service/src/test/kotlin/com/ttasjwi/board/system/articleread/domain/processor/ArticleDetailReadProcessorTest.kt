package com.ttasjwi.board.system.articleread.domain.processor

import com.ttasjwi.board.system.articleread.domain.dto.ArticleDetailReadQuery
import com.ttasjwi.board.system.articleread.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articleread.domain.model.fixture.articleDetailFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleDetailStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleViewCountStorageFixture
import com.ttasjwi.board.system.articleread.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


@DisplayName("[article-read-application-service] ArticleDetailReadProcessor 테스트")
class ArticleDetailReadProcessorTest {

    private lateinit var articleDetailReadProcessor :ArticleDetailReadProcessor
    private lateinit var articleDetailStorageFixture: ArticleDetailStorageFixture
    private lateinit var articleViewCountStorageFixture: ArticleViewCountStorageFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleDetailReadProcessor = container.articleDetailReadProcessor

        articleDetailStorageFixture = container.articleDetailStorageFixture
        articleViewCountStorageFixture = container.articleViewCountStorageFixture
    }


    @Test
    @DisplayName("성공 테스트 - 인증 사용자")
    fun testSuccess1() {
        // given
        val articleId= 15L
        val query = ArticleDetailReadQuery(
            articleId = articleId,
            authUser = authUserFixture(userId = 24L, role = Role.USER)
        )

        val articleDetail = articleDetailFixture(articleId = articleId)
        articleDetailStorageFixture.save(articleDetail)

        val viewCount = 8L
        articleViewCountStorageFixture.save(articleId, viewCount)

        // when
        val response = articleDetailReadProcessor.read(query)

        // then
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.title).isEqualTo(articleDetail.title)
        assertThat(response.content).isEqualTo(articleDetail.content)
        assertThat(response.board.boardId).isEqualTo(articleDetail.board.boardId.toString())
        assertThat(response.board.name).isEqualTo(articleDetail.board.name)
        assertThat(response.board.slug).isEqualTo(articleDetail.board.slug)
        assertThat(response.articleCategory.articleCategoryId).isEqualTo(articleDetail.articleCategory.articleCategoryId.toString())
        assertThat(response.articleCategory.name).isEqualTo(articleDetail.articleCategory.name)
        assertThat(response.articleCategory.slug).isEqualTo(articleDetail.articleCategory.slug)
        assertThat(response.articleCategory.allowSelfDelete).isEqualTo(articleDetail.articleCategory.allowSelfDelete)
        assertThat(response.articleCategory.allowLike).isEqualTo(articleDetail.articleCategory.allowLike)
        assertThat(response.articleCategory.allowDislike).isEqualTo(articleDetail.articleCategory.allowDislike)
        assertThat(response.writer.writerId).isEqualTo(articleDetail.writer.writerId.toString())
        assertThat(response.writer.nickname).isEqualTo(articleDetail.writer.nickname)
        assertThat(response.viewCount).isEqualTo(viewCount)
        assertThat(response.commentCount).isEqualTo(articleDetail.commentCount)
        assertThat(response.liked).isEqualTo(articleDetail.liked)
        assertThat(response.likeCount).isEqualTo(articleDetail.likeCount)
        assertThat(response.disliked).isEqualTo(articleDetail.disliked)
        assertThat(response.dislikeCount).isEqualTo(articleDetail.dislikeCount)
        assertThat(response.createdAt).isEqualTo(articleDetail.createdAt.toZonedDateTime())
        assertThat(response.modifiedAt).isEqualTo(articleDetail.modifiedAt.toZonedDateTime())
    }


    @Test
    @DisplayName("성공 테스트 - 미인증 사용자")
    fun testSuccess2() {
        // given
        val articleId= 15L
        val query = ArticleDetailReadQuery(
            articleId = articleId,
            authUser = null
        )

        val articleDetail = articleDetailFixture(articleId = articleId)
        articleDetailStorageFixture.save(articleDetail)

        val viewCount = 8L
        articleViewCountStorageFixture.save(articleDetail.articleId, viewCount)

        // when
        val response = articleDetailReadProcessor.read(query)

        // then
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.title).isEqualTo(articleDetail.title)
        assertThat(response.content).isEqualTo(articleDetail.content)
        assertThat(response.board.boardId).isEqualTo(articleDetail.board.boardId.toString())
        assertThat(response.board.name).isEqualTo(articleDetail.board.name)
        assertThat(response.board.slug).isEqualTo(articleDetail.board.slug)
        assertThat(response.articleCategory.articleCategoryId).isEqualTo(articleDetail.articleCategory.articleCategoryId.toString())
        assertThat(response.articleCategory.name).isEqualTo(articleDetail.articleCategory.name)
        assertThat(response.articleCategory.slug).isEqualTo(articleDetail.articleCategory.slug)
        assertThat(response.articleCategory.allowSelfDelete).isEqualTo(articleDetail.articleCategory.allowSelfDelete)
        assertThat(response.articleCategory.allowLike).isEqualTo(articleDetail.articleCategory.allowLike)
        assertThat(response.articleCategory.allowDislike).isEqualTo(articleDetail.articleCategory.allowDislike)
        assertThat(response.writer.writerId).isEqualTo(articleDetail.writer.writerId.toString())
        assertThat(response.writer.nickname).isEqualTo(articleDetail.writer.nickname)
        assertThat(response.viewCount).isEqualTo(viewCount)
        assertThat(response.commentCount).isEqualTo(articleDetail.commentCount)
        assertThat(response.liked).isEqualTo(articleDetail.liked)
        assertThat(response.likeCount).isEqualTo(articleDetail.likeCount)
        assertThat(response.disliked).isEqualTo(articleDetail.disliked)
        assertThat(response.dislikeCount).isEqualTo(articleDetail.dislikeCount)
        assertThat(response.createdAt).isEqualTo(articleDetail.createdAt.toZonedDateTime())
        assertThat(response.modifiedAt).isEqualTo(articleDetail.modifiedAt.toZonedDateTime())
    }


    @Test
    @DisplayName("실패 테스트 - 게시글 없을 때")
    fun testNotFound() {
        // given
        val articleId= 15L
        val query = ArticleDetailReadQuery(
            articleId = articleId,
            authUser = authUserFixture(userId = 24L, role = Role.USER)
        )

        val viewCount = 8L
        articleViewCountStorageFixture.save(articleId, viewCount)

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleDetailReadProcessor.read(query)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", articleId)
    }
}
