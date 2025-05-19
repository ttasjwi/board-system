package com.ttasjwi.board.system.articlelike.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCountReadResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCountReadUseCase
import com.ttasjwi.board.system.articlelike.test.base.ArticleLikeRestDocsTest
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

@DisplayName("[article-like-web-adapter] ArticleLikeCountReadController 테스트")
@WebMvcTest(ArticleLikeCountReadController::class)
class ArticleLikeCountReadControllerTest : ArticleLikeRestDocsTest() {

    @MockkBean
    private lateinit var articleLikeCountReadUseCase: ArticleLikeCountReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}/likes/count"
        val articleId = 134L

        val response = ArticleLikeCountReadResponse(
            articleId = articleId.toString(),
            likeCount = 2324L,
        )

        every { articleLikeCountReadUseCase.readLikeCount(articleId) } returns response

        mockMvc
            .perform(
                request(HttpMethod.GET, urlPattern, articleId)
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.likeCount").value(response.likeCount),
            )
            .andDocument(
                identifier = "article-like-count-read-success",
                pathParameters(
                    "articleId"
                            paramMeans "게시글 식별자"
                            constraint "실제 게시글이 존재해야함."
                ),
                responseBody(
                    "articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "likeCount"
                            type NUMBER
                            means "좋아요 수",
                )
            )
        verify(exactly = 1) { articleLikeCountReadUseCase.readLikeCount(articleId) }
    }
}
