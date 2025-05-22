package com.ttasjwi.board.system.articleview.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articleview.domain.ArticleViewCountIncreaseUseCase
import com.ttasjwi.board.system.articleview.test.base.ArticleViewRestDocsTest
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ArticleViewCountIncreaseController::class)
@DisplayName("[article-view-web-adapter] ArticleViewCountIncreaseController 테스트")
class ArticleViewCountIncreaseControllerTest : ArticleViewRestDocsTest() {

    @MockkBean
    private lateinit var articleViewCountIncreaseUseCase: ArticleViewCountIncreaseUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}/views"
        val articleId = 134L
        val accessToken = generateAccessToken(
            userId = 1557,
            role = Role.USER,
            issuedAt = appDateTimeFixture(),
            expiresAt = appDateTimeFixture(minute = 30)
        )
        val currentTime = appDateTimeFixture(minute = 18)
        changeCurrentTime(currentTime)

        every { articleViewCountIncreaseUseCase.increase(articleId) } just Runs

        mockMvc
            .perform(
                request(HttpMethod.POST, urlPattern, articleId)
                    .header("Authorization", "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isOk,
            )
            .andDocument(
                identifier = "article-view-count-increase-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                            example "Bearer 액세스토큰",
                ),
                pathParameters(
                    "articleId"
                            paramMeans "게시글 식별자"
                            constraint "실제 게시글이 존재해야함."
                ),
            )
        verify(exactly = 1) { articleViewCountIncreaseUseCase.increase(articleId) }
    }

    @Test
    @DisplayName("미인증 사용자는 좋아요할 수 없다.")
    fun testUnauthenticated() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}/views"
        val articleId = 134L

        mockMvc
            .perform(
                request(HttpMethod.POST, urlPattern, articleId)
            )
            .andExpectAll(
                status().isUnauthorized,
            )
    }
}
