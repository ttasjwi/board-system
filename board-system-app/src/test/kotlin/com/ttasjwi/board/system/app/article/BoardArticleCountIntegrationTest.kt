package com.ttasjwi.board.system.app.article

import com.ttasjwi.board.system.article.domain.ArticleCreateRequest
import com.ttasjwi.board.system.article.domain.ArticleCreateUseCase
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.article.domain.port.BoardArticleCountPersistencePort
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
@DisplayName("[app] 게시판 게시글수 통합테스트")
class BoardArticleCountIntegrationTest {

    @Autowired
    private lateinit var articlePersistencePort: ArticlePersistencePort

    @Autowired
    private lateinit var articleCategoryPersistencePort: ArticleCategoryPersistencePort

    @Autowired
    private lateinit var articleCreateUseCase: ArticleCreateUseCase

    @Autowired
    private lateinit var boardArticleCountPersistencePort: BoardArticleCountPersistencePort

    @Autowired
    private lateinit var userPersistencePort: UserPersistencePort

    @Test
    @DisplayName("게시글 수 동시성 테스트 : 동시 사용자가 많을 때, 게시글 수")
    fun boardArticleCountConcurrencyTest() {
        val threadCount = 100
        val userCount = 3000
        val boardArticleArticleCategoryId = 4314135L

        val executorService = Executors.newFixedThreadPool(threadCount)

        createArticles(
            executorService = executorService,
            userCount = userCount,
            boardId = boardArticleArticleCategoryId,
            articleId = boardArticleArticleCategoryId,
            articleCategoryId = boardArticleArticleCategoryId
        )
        executorService.shutdown()
    }


    private fun createArticles(
        executorService: ExecutorService,
        userCount: Int,
        boardId: Long,
        articleId: Long,
        articleCategoryId: Long
    ) {
        prepareArticleCategory(boardId, articleCategoryId)
        val latch = CountDownLatch(userCount)
        println("--------------------------------------------------------------------------")
        println("start create articles : boardId = $boardId")
        val start = System.nanoTime()
        for (i in 1..userCount) {
            val userId = i.toLong()

            executorService.execute {
                try {
                    createArticle(articleId, userId, articleCategoryId)
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

        val articleCount = boardArticleCountPersistencePort.findByIdOrNull(boardId)!!.articleCount

        println("end create articles : boardId = $boardId")
        println("count = $articleCount")
        println("--------------------------------------------------------------------------")

        assertThat(articleCount).isEqualTo(userCount.toLong())
    }

    private fun prepareArticleCategory(boardId: Long, articleCategoryId: Long) {
        articleCategoryPersistencePort.save(
            articleCategoryFixture(
                articleCategoryId = articleCategoryId,
                name = "테스트",
                slug = "test",
                boardId = boardId,
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
            )
        )
    }

    private fun createArticle(articleId: Long, userId: Long, articleCategoryId: Long) {
        setAuthUser(userId)
        prepareArticleWriter(userId)
        articleCreateUseCase.create(
            ArticleCreateRequest(
                title = "article-title-$articleId",
                content = "article-content-$articleId",
                articleCategoryId = articleCategoryId,
            )
        )
    }

    private fun prepareArticleWriter(userId: Long) {
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
