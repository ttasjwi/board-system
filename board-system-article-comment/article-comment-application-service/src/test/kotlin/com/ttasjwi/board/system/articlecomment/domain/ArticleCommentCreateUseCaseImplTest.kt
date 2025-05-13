package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentWriterNicknamePersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-application-service] ArticleCommentCreateUseCaseImpl")
class ArticleCommentCreateUseCaseImplTest {

    private lateinit var useCase: ArticleCommentCreateUseCase
    private lateinit var currentTime: AppDateTime
    private lateinit var articleCommentWriter: AuthUser
    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleCommentWriterNicknamePersistencePortFixture: ArticleCommentWriterNicknamePersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        useCase = container.articleCommentCreateUseCase

        currentTime = appDateTimeFixture(minute = 13)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        articleCommentWriter = authUserFixture(userId = 123456L)
        container.authUserLoaderFixture.changeAuthUser(articleCommentWriter)

        articleCommentPersistencePortFixture = container.articleCommentPersistencePortFixture
        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleCommentWriterNicknamePersistencePortFixture =
            container.articleCommentWriterNicknamePersistencePortFixture
    }

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val article = articlePersistencePortFixture.save(
            articleFixture()
        )
        val articleCommentWriterNickname = articleCommentWriterNicknamePersistencePortFixture.save(
            commentWriterId = articleCommentWriter.userId,
            commentWriterNickname = "땃쥐"
        )
        val request = ArticleCommentCreateRequest(
            content = "댓글 본문",
            articleId = article.articleId,
            parentCommentId = null,
        )

        // when
        val response = useCase.create(request)

        // then
        val findArticleComment = articleCommentPersistencePortFixture.findById(response.articleCommentId.toLong())!!

        assertThat(response.articleCommentId).isNotNull()
        assertThat(response.content).isEqualTo(request.content)
        assertThat(response.articleId).isEqualTo(request.articleId.toString())
        assertThat(response.rootParentCommentId).isEqualTo(response.articleCommentId)
        assertThat(response.writerId).isEqualTo(articleCommentWriter.userId.toString())
        assertThat(response.writerNickname).isEqualTo(articleCommentWriterNickname)
        assertThat(response.parentCommentWriterId).isNull()
        assertThat(response.parentCommentWriterNickname).isNull()
        assertThat(response.deleteStatus).isEqualTo(ArticleCommentDeleteStatus.NOT_DELETED.name)
        assertThat(response.createdAt).isEqualTo(currentTime.toZonedDateTime())
        assertThat(response.modifiedAt).isEqualTo(currentTime.toZonedDateTime())

        assertThat(findArticleComment.articleCommentId).isEqualTo(response.articleCommentId.toLong())
    }
}
