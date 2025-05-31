package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.model.ArticleCategory
import com.ttasjwi.board.system.article.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-application-service] ArticleCreateUseCaseImpl : 게시글 생성 유즈케이스 구현체")
class ArticleCreateUseCaseImplTest {

    private lateinit var useCase: ArticleCreateUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var writer: AuthUser
    private lateinit var writerNickname: String
    private lateinit var currentTime: AppDateTime
    private lateinit var savedArticleCategory: ArticleCategory

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articlePersistencePortFixture = container.articlePersistencePortFixture
        useCase = container.articleCreateUseCase

        currentTime = appDateTimeFixture(minute = 14)
        writer = authUserFixture(userId = 1234558L, role = Role.USER)
        writerNickname = "땃쥐"

        container.timeManagerFixture.changeCurrentTime(currentTime)
        container.authUserLoaderFixture.changeAuthUser(writer)
        container.articleWriterNicknamePersistencePortFixture.save(
            writerId = writer.userId,
            writerNickname = writerNickname,
        )
        savedArticleCategory = container.articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = 12314L,
                boardId = 87719111L,
                allowSelfEditDelete = true
            )
        )
    }

    @Test
    @DisplayName("입력값들이 유효하면 명령이 생성된다.")
    fun successTest() {
        // given
        val request = ArticleCreateRequest(
            title = "제목",
            content = "본문",
            articleCategoryId = savedArticleCategory.articleCategoryId
        )

        // when
        val response = useCase.create(request)

        // then
        val findArticle = articlePersistencePortFixture.findByIdOrNull(response.articleId.toLong())!!

        assertThat(response.articleId).isNotNull()
        assertThat(response.title).isEqualTo(request.title)
        assertThat(response.content).isEqualTo(request.content)
        assertThat(response.boardId).isEqualTo(savedArticleCategory.boardId.toString())
        assertThat(response.articleCategoryId).isEqualTo(request.articleCategoryId.toString())
        assertThat(response.writerId).isEqualTo(writer.userId.toString())
        assertThat(response.writerNickname).isEqualTo(writerNickname)
        assertThat(response.createdAt).isEqualTo(currentTime.toZonedDateTime())
        assertThat(response.modifiedAt).isEqualTo(currentTime.toZonedDateTime())
        assertThat(findArticle.title).isEqualTo(request.title)
        assertThat(findArticle.content).isEqualTo(request.content)
    }
}
