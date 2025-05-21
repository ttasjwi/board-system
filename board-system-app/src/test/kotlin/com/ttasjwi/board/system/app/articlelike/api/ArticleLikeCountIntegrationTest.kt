package com.ttasjwi.board.system.app.articlelike.api

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
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
    private lateinit var articleLikeUseCase: ArticleLikeCreateUseCase

    @Autowired
    private lateinit var articleLikeCountReadUseCase: ArticleLikeCountReadUseCase

    @Test
    @DisplayName("좋아요 수 동시성 테스트 : 동시 사용자가 많을 때, 좋아요 수")
    fun likeCountConcurrencyTest() {
        val executorService = Executors.newFixedThreadPool(100)
        likeCountTest(executorService, 111L, 111L, 111L)
        executorService.shutdown()
    }

    private fun likeCountTest(
        executorService: ExecutorService,
        boardId: Long,
        articleId: Long,
        articleCategoryId: Long
    ) {

        val userCount = 3000
        prepareArticleCategory(boardId, articleCategoryId)
        prepareArticle(boardId, articleCategoryId, articleId)

        val latch = CountDownLatch(userCount)
        println("--------------------------------------------------------------------------")
        println("start")
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

        println("end")
        println("count = ${response.likeCount}")
        println("--------------------------------------------------------------------------")

        assertThat(response.likeCount).isNotEqualTo(userCount)
    }

    private fun like(articleId: Long, userId: Long) {
        setAuthUser(userId)
        articleLikeUseCase.like(articleId)
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
                allowLike = true,
                allowDislike = true
            )
        )
    }
}
