package com.ttasjwi.board.system.app.articlelike

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCancelUseCase
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCountReadUseCase
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateUseCase
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
@DisplayName("[app] 게시글 좋아요 수 통합테스트")
class ArticleLikeCountIntegrationTest {

    @Autowired
    private lateinit var articlePersistencePort: ArticlePersistencePort

    @Autowired
    private lateinit var articleCategoryPersistencePort: ArticleCategoryPersistencePort

    @Autowired
    private lateinit var articleLikeCreateUseCase: ArticleLikeCreateUseCase

    @Autowired
    private lateinit var articleLikeCancelUseCase: ArticleLikeCancelUseCase

    @Autowired
    private lateinit var articleLikeCountReadUseCase: ArticleLikeCountReadUseCase

    @Test
    @DisplayName("좋아요 수 동시성 테스트 : 동시 사용자가 많을 때, 좋아요 수")
    fun likeCountConcurrencyTest() {
        val threadCount = 100
        val userCount = 3000
        val boardArticleArticleCategoryId = 13413413413L

        val executorService = Executors.newFixedThreadPool(threadCount)

        createLikes(
            executorService = executorService,
            userCount = userCount,
            boardId = boardArticleArticleCategoryId,
            articleId = boardArticleArticleCategoryId,
            articleCategoryId = boardArticleArticleCategoryId
        )
        cancelLikes(
            executorService = executorService,
            userCount = userCount,
            articleId = boardArticleArticleCategoryId,
        )
        executorService.shutdown()
    }

    private fun createLikes(
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
        println("start create Likes : articleId = $articleId")
        val start = System.nanoTime()
        for (i in 1..userCount) {
            val userId = i.toLong()

            executorService.execute {
                try {
                    like(articleId, userId)
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

        val response = articleLikeCountReadUseCase.readLikeCount(
            articleId = articleId,
        )

        println("end create Likes : articleId = $articleId")
        println("count = ${response.likeCount}")
        println("--------------------------------------------------------------------------")

        assertThat(response.likeCount).isEqualTo(userCount.toLong())
    }

    private fun cancelLikes(
        executorService: ExecutorService,
        userCount: Int,
        articleId: Long,
    ) {
        val latch = CountDownLatch(userCount)
        println("--------------------------------------------------------------------------")
        println("start cancel Likes : articleId = $articleId")
        val start = System.nanoTime()
        for (i in 1..userCount) {
            val userId = i.toLong()

            executorService.execute {
                try {
                    cancelLike(articleId, userId)
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

        val response = articleLikeCountReadUseCase.readLikeCount(
            articleId = articleId,
        )

        println("end cancel Likes : articleId = $articleId")
        println("count = ${response.likeCount}")
        println("--------------------------------------------------------------------------")

        assertThat(response.likeCount).isEqualTo(0)
    }


    private fun like(articleId: Long, userId: Long) {
        setAuthUser(userId)
        articleLikeCreateUseCase.like(articleId)
    }

    private fun cancelLike(articleId: Long, userId: Long) {
        setAuthUser(userId)
        articleLikeCancelUseCase.cancelLike(articleId)
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
