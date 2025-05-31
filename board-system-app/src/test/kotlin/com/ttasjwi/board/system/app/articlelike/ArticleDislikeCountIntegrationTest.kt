package com.ttasjwi.board.system.app.articlelike

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articlelike.domain.*
import com.ttasjwi.board.system.board.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.board.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.infra.websupport.auth.security.AuthUserAuthentication
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
@DisplayName("[app] 게시글 싫어요 수 통합테스트")
class ArticleDislikeCountIntegrationTest {

    @Autowired
    private lateinit var articlePersistencePort: ArticlePersistencePort

    @Autowired
    private lateinit var articleCategoryPersistencePort: ArticleCategoryPersistencePort

    @Autowired
    private lateinit var articleDislikeCreateUseCase: ArticleDislikeCreateUseCase

    @Autowired
    private lateinit var articleDislikeCancelUseCase: ArticleDislikeCancelUseCase

    @Autowired
    private lateinit var articleDislikeCountReadUseCase: ArticleDislikeCountReadUseCase

    @Test
    @DisplayName("싫어요 수 동시성 테스트 : 동시 사용자가 많을 때, 싫어요 수")
    fun dislikeCountConcurrencyTest() {
        val threadCount = 100
        val userCount = 3000
        val boardArticleArticleCategoryId = 171531L

        val executorService = Executors.newFixedThreadPool(threadCount)

        createDislikes(
            executorService = executorService,
            userCount = userCount,
            boardId = boardArticleArticleCategoryId,
            articleId = boardArticleArticleCategoryId,
            articleCategoryId = boardArticleArticleCategoryId
        )
        cancelDislikes(
            executorService = executorService,
            userCount = userCount,
            articleId = boardArticleArticleCategoryId,
        )
        executorService.shutdown()
    }

    private fun createDislikes(
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
        println("start create Dislikes : articleId = $articleId")
        val start = System.nanoTime()
        for (i in 1..userCount) {
            val userId = i.toLong()

            executorService.execute {
                try {
                    dislike(articleId, userId)
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

        val response = articleDislikeCountReadUseCase.readDislikeCount(
            articleId = articleId,
        )

        println("end create Dislikes : articleId = $articleId")
        println("count = ${response.dislikeCount}")
        println("--------------------------------------------------------------------------")

        assertThat(response.dislikeCount).isEqualTo(userCount.toLong())
    }

    private fun cancelDislikes(
        executorService: ExecutorService,
        userCount: Int,
        articleId: Long,
    ) {
        val latch = CountDownLatch(userCount)
        println("--------------------------------------------------------------------------")
        println("start cancel Dislikes : articleId = $articleId")
        val start = System.nanoTime()
        for (i in 1..userCount) {
            val userId = i.toLong()

            executorService.execute {
                try {
                    cancelDislike(articleId, userId)
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

        val response = articleDislikeCountReadUseCase.readDislikeCount(
            articleId = articleId,
        )

        println("end cancel Dislikes : articleId = $articleId")
        println("count = ${response.dislikeCount}")
        println("--------------------------------------------------------------------------")

        assertThat(response.dislikeCount).isEqualTo(0)
    }


    private fun dislike(articleId: Long, userId: Long) {
        setAuthUser(userId)
        articleDislikeCreateUseCase.dislike(articleId)
    }

    private fun cancelDislike(articleId: Long, userId: Long) {
        setAuthUser(userId)
        articleDislikeCancelUseCase.cancelDislike(articleId)
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
                allowWrite = true,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = true,
                allowDislike = true,
            )
        )
    }
}
