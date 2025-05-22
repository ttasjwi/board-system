package com.ttasjwi.board.system.articlecomment.domain.processor

import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentCreateCommand
import com.ttasjwi.board.system.articlecomment.domain.exception.*
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentCountPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentWriterNicknamePersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-comment-application-service] ArticleCommentCreateProcessor 테스트")
class ArticleCommentCreateProcessorTest {

    private lateinit var processor: ArticleCommentCreateProcessor
    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePortFixture
    private lateinit var articleCommentCountPersistencePortfixture: ArticleCommentCountPersistencePortFixture
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleCommentWriterNicknamePersistencePortFixture: ArticleCommentWriterNicknamePersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        processor = container.articleCommentCreateProcessor
        articleCommentPersistencePortFixture = container.articleCommentPersistencePortFixture
        articleCommentCountPersistencePortfixture = container.articleCommentCountPersistencePortFixture
        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleCommentWriterNicknamePersistencePortFixture =
            container.articleCommentWriterNicknamePersistencePortFixture
    }


    @Test
    @DisplayName("성공 테스트 - 부모댓글이 없을 때")
    fun testSuccess1() {
        // given
        val commentWriter = authUserFixture(userId = 1234L)
        val article = articlePersistencePortFixture.save(
            articleFixture()
        )
        val articleCommentWriterNickname = articleCommentWriterNicknamePersistencePortFixture.save(
            commentWriterId = commentWriter.userId,
            commentWriterNickname = "땃쥐"
        )

        val command = ArticleCommentCreateCommand(
            content = "댓글 본문",
            articleId = article.articleId,
            parentCommentId = null,
            currentTime = appDateTimeFixture(),
            commentWriter = commentWriter
        )

        // when
        val articleComment = processor.create(command)

        // then
        val findArticleComment = articleCommentPersistencePortFixture.findById(articleComment.articleCommentId)!!
        val findArticleCommentCount = articleCommentCountPersistencePortfixture.findByIdOrNull(articleComment.articleId)!!

        assertThat(articleComment.articleCommentId).isNotNull()
        assertThat(articleComment.content).isEqualTo(command.content)
        assertThat(articleComment.articleId).isEqualTo(command.articleId)
        assertThat(articleComment.rootParentCommentId).isEqualTo(articleComment.articleCommentId)
        assertThat(articleComment.writerId).isEqualTo(commentWriter.userId)
        assertThat(articleComment.writerNickname).isEqualTo(articleCommentWriterNickname)
        assertThat(articleComment.parentCommentWriterId).isNull()
        assertThat(articleComment.parentCommentWriterNickname).isNull()
        assertThat(articleComment.deleteStatus).isEqualTo(ArticleCommentDeleteStatus.NOT_DELETED)
        assertThat(articleComment.createdAt).isEqualTo(command.currentTime)
        assertThat(articleComment.modifiedAt).isEqualTo(command.currentTime)

        assertThat(findArticleComment.articleCommentId).isEqualTo(articleComment.articleCommentId)
        assertThat(findArticleCommentCount.articleId).isEqualTo(articleComment.articleId)
        assertThat(findArticleCommentCount.commentCount).isEqualTo(1)
    }


    @Test
    @DisplayName("성공 테스트 - 부모댓글이 있을 때")
    fun testSuccess2() {
        // given
        val commentWriter = authUserFixture(userId = 1234L)
        val article = articlePersistencePortFixture.save(
            articleFixture()
        )
        val articleCommentWriterNickname = articleCommentWriterNicknamePersistencePortFixture.save(
            commentWriterId = commentWriter.userId,
            commentWriterNickname = "땃쥐"
        )
        val parentComment = articleCommentPersistencePortFixture.save(
            articleCommentFixture(
                articleCommentId = 12345L,
                articleId = article.articleId,
                rootParentCommentId = 12345L,
                writerId = 55555L,
                writerNickname = "땃고양이",
                parentCommentWriterId = null,
                parentCommentWriterNickname = null,
                deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED
            )
        )
        articleCommentCountPersistencePortfixture.increase(article.articleId)

        val command = ArticleCommentCreateCommand(
            content = "댓글 본문",
            articleId = article.articleId,
            parentCommentId = parentComment.articleCommentId,
            currentTime = appDateTimeFixture(),
            commentWriter = commentWriter
        )

        // when
        val articleComment = processor.create(command)

        // then
        val findArticleComment = articleCommentPersistencePortFixture.findById(articleComment.articleCommentId)!!
        val findArticleCommentCount = articleCommentCountPersistencePortfixture.findByIdOrNull(articleComment.articleId)!!

        assertThat(articleComment.articleCommentId).isNotNull()
        assertThat(articleComment.content).isEqualTo(command.content)
        assertThat(articleComment.articleId).isEqualTo(command.articleId)
        assertThat(articleComment.rootParentCommentId).isEqualTo(parentComment.rootParentCommentId)
        assertThat(articleComment.writerId).isEqualTo(commentWriter.userId)
        assertThat(articleComment.writerNickname).isEqualTo(articleCommentWriterNickname)
        assertThat(articleComment.parentCommentWriterId).isEqualTo(parentComment.writerId)
        assertThat(articleComment.parentCommentWriterNickname).isEqualTo(parentComment.writerNickname)
        assertThat(articleComment.deleteStatus).isEqualTo(ArticleCommentDeleteStatus.NOT_DELETED)
        assertThat(articleComment.createdAt).isEqualTo(command.currentTime)
        assertThat(articleComment.modifiedAt).isEqualTo(command.currentTime)

        assertThat(findArticleComment.articleCommentId).isEqualTo(articleComment.articleCommentId)
        assertThat(findArticleCommentCount.articleId).isEqualTo(articleComment.articleId)
        assertThat(findArticleCommentCount.commentCount).isEqualTo(2)
    }


    @Test
    @DisplayName("게시글이 존재하지 않을 때 예외 발생")
    fun testFailure1() {
        // given
        val commentWriter = authUserFixture(userId = 1234L)

        val command = ArticleCommentCreateCommand(
            content = "댓글 본문",
            articleId = 134134134134L,
            parentCommentId = null,
            currentTime = appDateTimeFixture(),
            commentWriter = commentWriter
        )

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            processor.create(command)
        }
        assertThat(exception.source).isEqualTo("articleId")
        assertThat(exception.args).containsExactly("articleId", command.articleId)
    }

    @Test
    @DisplayName("부모댓글 Id가 명령에 있으나, 부모댓글 Id가 조회되지 않았을 때 예외 발생")
    fun testFailure2() {
        // given
        val commentWriter = authUserFixture(userId = 1234L)
        val article = articlePersistencePortFixture.save(
            articleFixture()
        )
        val command = ArticleCommentCreateCommand(
            content = "댓글 본문",
            articleId = article.articleId,
            parentCommentId = 134134134134L,
            currentTime = appDateTimeFixture(),
            commentWriter = commentWriter
        )

        // when
        val exception = assertThrows<ParentArticleCommentNotFoundException> {
            processor.create(command)
        }

        // then
        assertThat(exception.source).isEqualTo("parentCommentId")
        assertThat(exception.args).containsExactly(command.parentCommentId)
    }

    @Test
    @DisplayName("부모댓글이 게시글의 댓글이 아닐 경우 예외 발생")
    fun testFailure3() {
        // given
        val commentWriter = authUserFixture(userId = 1234L)
        val article = articlePersistencePortFixture.save(
            articleFixture()
        )
        val parentComment = articleCommentPersistencePortFixture.save(
            articleCommentFixture(
                articleCommentId = 12345L,
                articleId = 13413413413413413L,
                rootParentCommentId = 12345L,
                writerId = 55555L,
                writerNickname = "땃고양이",
                parentCommentWriterId = null,
                parentCommentWriterNickname = null,
                deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED
            )
        )

        val command = ArticleCommentCreateCommand(
            content = "댓글 본문",
            articleId = article.articleId,
            parentCommentId = parentComment.articleCommentId,
            currentTime = appDateTimeFixture(),
            commentWriter = commentWriter
        )

        // when
        val exception = assertThrows<InvalidParentArticleCommentException> {
            processor.create(command)
        }

        // then
        assertThat(exception.source).isEqualTo("parentCommentId")
        assertThat(exception.args).containsExactly(command.parentCommentId, command.articleId)
    }

    @Test
    @DisplayName("부모댓글이 논리적으로 삭제된 상태일 때 예외 발생")
    fun testFailure4() {
        // given
        val commentWriter = authUserFixture(userId = 1234L)
        val article = articlePersistencePortFixture.save(
            articleFixture()
        )
        val parentComment = articleCommentPersistencePortFixture.save(
            articleCommentFixture(
                articleCommentId = 12345L,
                articleId = article.articleId,
                rootParentCommentId = 12345L,
                writerId = 55555L,
                writerNickname = "땃고양이",
                parentCommentWriterId = null,
                parentCommentWriterNickname = null,
                deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_WRITER
            )
        )

        val command = ArticleCommentCreateCommand(
            content = "댓글 본문",
            articleId = article.articleId,
            parentCommentId = parentComment.articleCommentId,
            currentTime = appDateTimeFixture(),
            commentWriter = commentWriter
        )

        // when
        val exception = assertThrows<ParentCommentAlreadyDeletedException> {
            processor.create(command)
        }

        // then
        assertThat(exception.source).isEqualTo("parentCommentId")
        assertThat(exception.args).containsExactly(command.parentCommentId)
    }


    @Test
    @DisplayName("게시글 작성자의 닉네임 조회에 실패했을 경우 예외 발생")
    fun testFailure5() {
        // given
        val commentWriter = authUserFixture(userId = 1234L)
        val article = articlePersistencePortFixture.save(
            articleFixture()
        )
        val parentComment = articleCommentPersistencePortFixture.save(
            articleCommentFixture(
                articleCommentId = 12345L,
                articleId = article.articleId,
                rootParentCommentId = 12345L,
                writerId = 55555L,
                writerNickname = "땃고양이",
                parentCommentWriterId = null,
                parentCommentWriterNickname = null,
                deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED
            )
        )

        val command = ArticleCommentCreateCommand(
            content = "댓글 본문",
            articleId = article.articleId,
            parentCommentId = parentComment.articleCommentId,
            currentTime = appDateTimeFixture(),
            commentWriter = commentWriter
        )
        // when
        val exception = assertThrows<ArticleCommentWriterNicknameNotFoundException> {
            processor.create(command)
        }

        // then
        assertThat(exception.args).containsExactly(commentWriter.userId)
    }
}
