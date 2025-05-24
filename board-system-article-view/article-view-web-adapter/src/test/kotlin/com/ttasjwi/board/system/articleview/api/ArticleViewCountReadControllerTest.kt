package com.ttasjwi.board.system.articleview.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articleview.domain.ArticleViewCountReadResponse
import com.ttasjwi.board.system.articleview.domain.ArticleViewCountReadUseCase
import com.ttasjwi.board.system.articleview.test.base.ArticleViewRestDocsTest
import com.ttasjwi.board.system.test.util.*
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ArticleViewCountReadController::class)
@DisplayName("[article-view-web-adapter] ArticleViewCountReadController 테스트")
class ArticleViewCountReadControllerTest : ArticleViewRestDocsTest() {

    @MockkBean
    private lateinit var articleViewCountReadUseCase: ArticleViewCountReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}/views/count"
        val articleId = 134L

        val response = ArticleViewCountReadResponse(
            articleId = articleId.toString(),
            viewCount = 135L
        )

        every { articleViewCountReadUseCase.readViewCount(articleId) } returns response

        mockMvc
            .perform(
                request(HttpMethod.GET, urlPattern, articleId)
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.viewCount").value(response.viewCount),
            )
            .andDocument(
                identifier = "article-view-count-read-success",
                pathParameters(
                    "articleId"
                            paramMeans "게시글 식별자"
                            constraint "실제 게시글이 존재해야함."
                ),
                responseBody(
                    "articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "viewCount"
                            type NUMBER
                            means "게시글 조회수"
                )
            )
        verify(exactly = 1) { articleViewCountReadUseCase.readViewCount(articleId) }
    }
}
