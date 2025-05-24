package com.ttasjwi.board.system.articlecomment.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCountReadResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCountReadUseCase
import com.ttasjwi.board.system.articlecomment.test.base.ArticleCommentRestDocsTest
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

@WebMvcTest(ArticleCommentCountReadController::class)
@DisplayName("[article-comment-web-adapter] ArticleCommentCountReadController 테스트")
class ArticleCommentCountReadControllerTest : ArticleCommentRestDocsTest() {

    @MockkBean
    private lateinit var articleCommentCountReadUseCase: ArticleCommentCountReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}/comments/count"
        val articleId = 134L

        val response = ArticleCommentCountReadResponse(
            articleId = articleId.toString(),
            commentCount = 135L
        )

        every { articleCommentCountReadUseCase.readCommentCount(articleId) } returns response

        mockMvc
            .perform(
                request(HttpMethod.GET, urlPattern, articleId)
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.commentCount").value(response.commentCount),
            )
            .andDocument(
                identifier = "article-comment-count-read-success",
                pathParameters(
                    "articleId"
                            paramMeans "게시글 식별자"
                            constraint "실제 게시글이 존재해야함."
                ),
                responseBody(
                    "articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "commentCount"
                            type NUMBER
                            means "게시글 댓글수"
                )
            )
        verify(exactly = 1) { articleCommentCountReadUseCase.readCommentCount(articleId) }
    }
}
