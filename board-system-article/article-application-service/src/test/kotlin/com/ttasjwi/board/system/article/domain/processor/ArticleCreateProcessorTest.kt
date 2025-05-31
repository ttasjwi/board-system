package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.dto.ArticleCreateCommand
import com.ttasjwi.board.system.article.domain.exception.ArticleCategoryNotFoundException
import com.ttasjwi.board.system.article.domain.exception.ArticleWriterNicknameNotFoundException
import com.ttasjwi.board.system.article.domain.model.ArticleCategory
import com.ttasjwi.board.system.article.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticleWriterNicknamePersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-application-service] ArticleCreateProcessor : 게시글 생성 애플리케이션 프로세서")
class ArticleCreateProcessorTest {

    private lateinit var processor: ArticleCreateProcessor
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleWriterNicknamePersistencePortFixture: ArticleWriterNicknamePersistencePortFixture
    private lateinit var savedArticleCategory: ArticleCategory

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        processor = container.articleCreateProcessor
        articleWriterNicknamePersistencePortFixture = container.articleWriterNicknamePersistencePortFixture

        savedArticleCategory = container.articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = 12314L,
                boardId = 87719111L,
                allowSelfEditDelete = true
            )
        )
        articlePersistencePortFixture = container.articlePersistencePortFixture
    }

    @Test
    @DisplayName("성공 테스트")
    fun successTest() {
        // given
        val writerNickname = "땃고양이"
        val command = ArticleCreateCommand(
            title = "제목",
            content = "본문",
            articleCategoryId = savedArticleCategory.articleCategoryId,
            writer = authUserFixture(userId = 15747L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 43)
        )
        articleWriterNicknamePersistencePortFixture.save(
            writerId = command.writer.userId,
            writerNickname = writerNickname
        )

        // when
        val article = processor.create(command)

        // then
        val findArticle = articlePersistencePortFixture.findByIdOrNull(article.articleId)!!

        assertThat(article.articleId).isNotNull()
        assertThat(article.title).isEqualTo(command.title)
        assertThat(article.content).isEqualTo(command.content)
        assertThat(article.boardId).isEqualTo(savedArticleCategory.boardId)
        assertThat(article.articleCategoryId).isEqualTo(command.articleCategoryId)
        assertThat(article.writerId).isEqualTo(command.writer.userId)
        assertThat(article.writerNickname).isEqualTo(writerNickname)
        assertThat(article.createdAt).isEqualTo(command.currentTime)
        assertThat(article.modifiedAt).isEqualTo(command.currentTime)

        assertThat(findArticle.title).isEqualTo(article.title)
        assertThat(findArticle.content).isEqualTo(command.content)
    }

    @Test
    @DisplayName("게시글 작성자 닉네임을 조회하는데 실패하면 예외가 발생한다.")
    fun failureWhenArticleWriterNotFoundTest() {
        // given
        val command = ArticleCreateCommand(
            title = "제목",
            content = "본문",
            articleCategoryId = 584236L,
            writer = authUserFixture(userId = 15747L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 43)
        )

        // when
        val ex = assertThrows<ArticleWriterNicknameNotFoundException> {
            processor.create(command)
        }

        // then
        assertThat(ex.message).isEqualTo("게시글 작성을 시도했으나, 사용자 닉네임을 조회하는데 실패했습니다. 사용자가 탈퇴했을 가능성이 있습니다. (articleWriterId = ${command.writer.userId})")
    }

    @Test
    @DisplayName("게시글 카테고리를 조회하는데 실패하면 예외가 발생한다.")
    fun failureWhenArticleCategoryNotFoundTest() {
        // given
        articleWriterNicknamePersistencePortFixture
        val command = ArticleCreateCommand(
            title = "제목",
            content = "본문",
            articleCategoryId = 584236L,
            writer = authUserFixture(userId = 15747L, role = Role.USER),
            currentTime = appDateTimeFixture(minute = 43)
        )
        articleWriterNicknamePersistencePortFixture.save(
            writerId = command.writer.userId,
            writerNickname = "땃고양이"
        )

        // when
        val ex = assertThrows<ArticleCategoryNotFoundException> {
            processor.create(command)
        }

        // then
        assertThat(ex.message).isEqualTo("조건에 부합하는 게시글 카테고리를 찾지 못 했습니다. (articleCategoryId = ${command.articleCategoryId})")
    }
}
