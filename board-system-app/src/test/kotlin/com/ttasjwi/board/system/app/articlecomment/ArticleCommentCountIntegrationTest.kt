package com.ttasjwi.board.system.app.articlecomment

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateRequest
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateUseCase
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentCountPersistencePort
import com.ttasjwi.board.system.board.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.board.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.infra.websupport.auth.security.AuthUserAuthentication
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.port.UserPersistencePort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.context.SecurityContextHolder
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Disabled // 수동테스트용(테스트 해보고 싶을 경우 주석처리)
@SpringBootTest
@DisplayName("[app] 게시글 댓글 수 통합테스트")
class ArticleCommentCountIntegrationTest {


    @Autowired
    private lateinit var articlePersistencePort: ArticlePersistencePort

    @Autowired
    private lateinit var articleCategoryPersistencePort: ArticleCategoryPersistencePort

    @Autowired
    private lateinit var articleCommentCreateUseCase: ArticleCommentCreateUseCase

    @Autowired
    private lateinit var articleCommentCountPersistencePort: ArticleCommentCountPersistencePort

    @Autowired
    private lateinit var userPersistencePort: UserPersistencePort

    @Test
    @DisplayName("댓글 수 동시성 테스트 : 동시 사용자가 많을 때, 댓글 수")
    fun commentCountConcurrencyTest() {
        val threadCount = 100
        val userCount = 3000
        val boardArticleArticleCategoryId = 4314135L

        val executorService = Executors.newFixedThreadPool(threadCount)

        createComments(
            executorService = executorService,
            userCount = userCount,
            boardId = boardArticleArticleCategoryId,
            articleId = boardArticleArticleCategoryId,
            articleCategoryId = boardArticleArticleCategoryId
        )
        executorService.shutdown()
    }


    private fun createComments(
        executorService: ExecutorService,
        userCount: Int,
        boardId: Long,
        articleId: Long,
        articleCategoryId: Long
    ) {
        prepareArticleCategory(boardId, articleCategoryId)
        prepareArticle(boardId, articleCategoryId, articleId)
        val latch = CountDownLatch(userCount)
        println("--------------------------------------------------------------------------")
        println("start create Comments : articleId = $articleId")
        val start = System.nanoTime()
        for (i in 1..userCount) {
            val userId = i.toLong()

            executorService.execute {
                try {
                    createComment(articleId, userId)
                } catch (e: Exception) {
                    println("Error for userId=$userId: ${e.message}")
                } finally {
                    latch.countDown()
                }
            }

        }
        latch.await()
        val end = System.nanoTime()
        println("time = ${(end - start) / 100_0000} ms")

        val commentCount = articleCommentCountPersistencePort.findByIdOrNull(articleId)!!.commentCount

        println("end create Comments : articleId = $articleId")
        println("count = $commentCount")
        println("--------------------------------------------------------------------------")

        assertThat(commentCount).isEqualTo(userCount.toLong())
    }

    private fun prepareArticle(boardId: Long, articleCategoryId: Long, articleId: Long) {
        articlePersistencePort.save(
            articleFixture(
                articleId = articleId,
                title = "article-title-$articleId",
                content = "article-content-$articleId",
                boardId = boardId,
                articleCategoryId = articleCategoryId,
                writerId = 1L
            )
        )
    }

    private fun prepareArticleCategory(boardId: Long, articleCategoryId: Long) {
        articleCategoryPersistencePort.save(
            articleCategoryFixture(
                articleCategoryId = articleCategoryId,
                name = "테스트",
                slug = "test",
                boardId = boardId,
                allowLike = true,
                allowDislike = true
            )
        )
    }

    private fun createComment(articleId: Long, userId: Long) {
        setAuthUser(userId)
        prepareCommentWriter(userId)
        articleCommentCreateUseCase.create(
            ArticleCommentCreateRequest(
                content = "comment-by-$userId",
                articleId = articleId,
                parentCommentId = null,
            )
        )
    }

    private fun prepareCommentWriter(userId: Long) {
        userPersistencePort.save(
            userFixture(
                userId = userId,
                email = "user$userId@gmail.com",
                password = "1234567890",
                username = "user_$userId",
                nickname = "USER$userId",
                role = Role.USER,
                registeredAt = appDateTimeFixture(minute = 13),
            )
        )
    }

    private fun setAuthUser(userId: Long) {
        val authentication = AuthUserAuthentication.from(
            authUser = authUserFixture(
                userId = userId,
                role = Role.USER
            )
        )
        val securityContext = SecurityContextHolder.getContextHolderStrategy().createEmptyContext()
        securityContext.authentication = authentication
        SecurityContextHolder.getContextHolderStrategy().context = securityContext
    }

}
