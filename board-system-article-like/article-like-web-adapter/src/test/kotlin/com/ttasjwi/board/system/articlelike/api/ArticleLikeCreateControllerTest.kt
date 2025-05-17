package com.ttasjwi.board.system.articlelike.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateRequest
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateResponse
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateUseCase
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
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("[article-like-web-adapter] ArticleLikeCreateController 테스트")
@WebMvcTest(ArticleLikeCreateController::class)
class ArticleLikeCreateControllerTest : ArticleLikeRestDocsTest() {

    @MockkBean
    private lateinit var articleLikeCreateUseCase: ArticleLikeCreateUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/article-likes"
        val request = ArticleLikeCreateRequest(
            articleId = 134L,
        )
        val accessToken = generateAccessToken(
            userId = 1557,
            role = Role.USER,
            issuedAt = appDateTimeFixture(),
            expiresAt = appDateTimeFixture(minute = 30)
        )
        val currentTime = appDateTimeFixture(minute = 18)
        changeCurrentTime(currentTime)

        val response = ArticleLikeCreateResponse(
            articleLikeId = "134564164146",
            articleId = request.articleId!!.toString(),
            userId = accessToken.authUser.userId.toString(),
            createdAt = currentTime.toZonedDateTime(),
        )

        every { articleLikeCreateUseCase.like(request) } returns response

        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
                    .header("Authorization", "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isCreated,
                jsonPath("$.articleLikeId").value(response.articleLikeId),
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.userId").value(response.userId),
                jsonPath("$.createdAt").value("2025-01-01T00:18:00+09:00"),
            )
            .andDocument(
                identifier = "article-like-create-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                            example "Bearer 액세스토큰",
                ),
                requestBody(
                    "articleId"
                            type NUMBER
                            means "게시글 식별자"
                            constraint "실제 게시글이 존재해야함."
                ),
                responseBody(
                    "articleLikeId"
                            type STRING
                            means "게시글 좋아요 식별자(Id)",
                    "articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "userId"
                            type STRING
                            means "사용자 식별자(Id)",
                    "createdAt"
                            type DATETIME
                            means "좋아요한 시각",
                )
            )
        verify(exactly = 1) { articleLikeCreateUseCase.like(request) }
    }

    @Test
    @DisplayName("미인증 사용자는 좋아요할 수 없다.")
    fun testUnauthenticated() {
        // given
        val url = "/api/v1/article-likes"
        val request = ArticleLikeCreateRequest(
            articleId = 134L,
        )

        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
            )
            .andExpectAll(
                status().isUnauthorized,
            )
    }
}
