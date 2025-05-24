package com.ttasjwi.board.system.app.articleview

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articleview.domain.ArticleViewCountIncreaseUseCase
import com.ttasjwi.board.system.articleview.domain.ArticleViewCountReadUseCase
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
@DisplayName("[app] 게시글 조회수 통합테스트")
class ArticleViewCountIntegrationTest {

    @Autowired
    private lateinit var articleViewCountIncreaseUseCase: ArticleViewCountIncreaseUseCase

    @Autowired
    private lateinit var articlePersistencePort: ArticlePersistencePort

    @Autowired
    private lateinit var articleCategoryPersistencePort: ArticleCategoryPersistencePort

    @Autowired
    private lateinit var articleViewCountReadUseCase: ArticleViewCountReadUseCase

    @Test
    @DisplayName("댓글 수 동시성 테스트 : 같은 사용자가 동시 조회수 어뷰징 요청하더라도, 조회수는 1 증가한다")
    fun viewCountConcurrencyTest() {
        val threadCount = 100
        val tryCount = 10000
        val boardArticleArticleCategoryUserId = 38314133141345L

        val executorService = Executors.newFixedThreadPool(threadCount)

        increaseViewCounts(
            executorService = executorService,
            tryCount = tryCount,
            boardId = boardArticleArticleCategoryUserId,
            articleId = boardArticleArticleCategoryUserId,
            articleCategoryId = boardArticleArticleCategoryUserId,
            userId = boardArticleArticleCategoryUserId
        )
        executorService.shutdown()
    }


    private fun increaseViewCounts(
        executorService: ExecutorService,
        tryCount: Int,
        boardId: Long,
        articleId: Long,
        articleCategoryId: Long,
        userId: Long,
    ) {
        prepareArticleCategory(boardId, articleCategoryId)
        prepareArticle(boardId, articleCategoryId, articleId)

        val latch = CountDownLatch(tryCount)
        println("--------------------------------------------------------------------------")
        println("start increase viewCounts : articleId = $articleId")
        val start = System.nanoTime()
        for (i in 1..tryCount) {
            executorService.execute {
                try {
                    increaseViewCount(articleId, userId)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    latch.countDown()
                }
            }

        }
        latch.await()
        val end = System.nanoTime()
        println("time = ${(end - start) / 100_0000} ms")

        val viewCount = articleViewCountReadUseCase.readViewCount(articleId).viewCount

        println("start increase viewCounts : articleId = $articleId")
        println("viewCount = $viewCount")
        println("--------------------------------------------------------------------------")

        assertThat(viewCount).isEqualTo(1L)
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

    private fun increaseViewCount(articleId: Long, userId: Long) {
        setAuthUser(userId)
        articleViewCountIncreaseUseCase.increase(articleId)
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
