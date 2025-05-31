package com.ttasjwi.board.system.articleread.domain

import com.ttasjwi.board.system.articleread.domain.model.fixture.articleDetailFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleDetailStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleViewCountStorageFixture
import com.ttasjwi.board.system.articleread.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.AuthUserLoaderFixture
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-application-service] ArticleDetailReadUseCaseImpl 테스트")
class ArticleDetailReadUseCaseImplTest {

    private lateinit var articleDetailReadUseCase: ArticleDetailReadUseCase
    private lateinit var authUserLoaderFixture: AuthUserLoaderFixture
    private lateinit var articleDetailStorageFixture: ArticleDetailStorageFixture
    private lateinit var articleViewCountStorageFixture: ArticleViewCountStorageFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        articleDetailReadUseCase = container.articleDetailReadUseCase

        authUserLoaderFixture = container.authUserLoaderFixture
        articleDetailStorageFixture = container.articleDetailStorageFixture
        articleViewCountStorageFixture = container.articleViewCountStorageFixture
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val articleId = 13455L
        val authUser = authUserFixture(userId = 34L, role = Role.USER)
        authUserLoaderFixture.changeAuthUser(authUser)

        val articleDetail = articleDetailFixture(articleId = articleId)
        articleDetailStorageFixture.save(articleDetail)

        val viewCount = 8L
        articleViewCountStorageFixture.save(articleId, viewCount)

        // when
        val response = articleDetailReadUseCase.read(articleId)

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
        assertThat(response.articleCategory.allowWrite).isEqualTo(articleDetail.articleCategory.allowWrite)
        assertThat(response.articleCategory.allowSelfEditDelete).isEqualTo(articleDetail.articleCategory.allowSelfEditDelete)
        assertThat(response.articleCategory.allowComment).isEqualTo(articleDetail.articleCategory.allowComment)
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
}
