package com.ttasjwi.board.system.articlelike.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCountReadResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCountReadUseCase
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

@DisplayName("[article-like-web-adapter] ArticleDislikeCountReadController 테스트")
@WebMvcTest(ArticleDislikeCountReadController::class)
class ArticleDislikeCountReadControllerTest : ArticleLikeRestDocsTest() {

    @MockkBean
    private lateinit var articleDislikeCountReadUseCase: ArticleDislikeCountReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}/dislikes/count"
        val articleId = 134L

        val response = ArticleDislikeCountReadResponse(
            articleId = articleId.toString(),
            dislikeCount = 255L,
        )

        every { articleDislikeCountReadUseCase.readDislikeCount(articleId) } returns response

        mockMvc
            .perform(
                request(HttpMethod.GET, urlPattern, articleId)
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.dislikeCount").value(response.dislikeCount),
            )
            .andDocument(
                identifier = "article-dislike-count-read-success",
                pathParameters(
                    "articleId"
                            paramMeans "게시글 식별자"
                            constraint "실제 게시글이 존재해야함."
                ),
                responseBody(
                    "articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "dislikeCount"
                            type NUMBER
                            means "싫어요 수",
                )
            )
        verify(exactly = 1) { articleDislikeCountReadUseCase.readDislikeCount(articleId) }
    }
}
