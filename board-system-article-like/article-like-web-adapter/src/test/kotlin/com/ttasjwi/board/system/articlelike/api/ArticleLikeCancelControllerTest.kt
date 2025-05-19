package com.ttasjwi.board.system.articlelike.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlelike.domain.*
import com.ttasjwi.board.system.articlelike.test.base.ArticleLikeRestDocsTest
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("[article-like-web-adapter] ArticleLikeCancelController 테스트")
@WebMvcTest(ArticleLikeCancelController::class)
class ArticleLikeCancelControllerTest : ArticleLikeRestDocsTest() {

    @MockkBean
    private lateinit var articleLikeCancelUseCase: ArticleLikeCancelUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}/likes"
        val articleId = 134L

        val accessToken = generateAccessToken(
            userId = 1557,
            role = Role.USER,
            issuedAt = appDateTimeFixture(),
            expiresAt = appDateTimeFixture(minute = 30)
        )
        val currentTime = appDateTimeFixture(minute = 18)
        changeCurrentTime(currentTime)

        val response = ArticleLikeCancelResponse(
            articleId = articleId.toString(),
            userId = accessToken.authUser.userId.toString(),
            canceledAt = currentTime.toZonedDateTime(),
        )

        every { articleLikeCancelUseCase.cancelLike(articleId) } returns response

        mockMvc
            .perform(
                request(HttpMethod.DELETE, urlPattern, articleId)
                    .header("Authorization", "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isNoContent,
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.userId").value(response.userId),
                jsonPath("$.canceledAt").value("2025-01-01T00:18:00+09:00"),
            )
            .andDocument(
                identifier = "article-like-cancel-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                            example "Bearer 액세스토큰",
                ),
                pathParameters(
                    "articleId"
                            paramMeans "게시글 식별자"
                            constraint "실제 게시글이 존재해야하고, '좋아요'한 게시글이여야 함."
                ),
                responseBody(
                    "articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "userId"
                            type STRING
                            means "사용자 식별자(Id)",
                    "canceledAt"
                            type DATETIME
                            means "좋아요 취소한 시각",
                )
            )
        verify(exactly = 1) { articleLikeCancelUseCase.cancelLike(articleId) }
    }

    @Test
    @DisplayName("미인증 사용자는 좋아요 취소할 수 없다.")
    fun testUnauthenticated() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}/likes"
        val articleId = 134L

        mockMvc
            .perform(
                request(HttpMethod.DELETE, urlPattern, articleId)
            )
            .andExpectAll(
                status().isUnauthorized,
            )
    }
}
