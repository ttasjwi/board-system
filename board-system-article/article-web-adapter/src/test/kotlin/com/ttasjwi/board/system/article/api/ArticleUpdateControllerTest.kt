package com.ttasjwi.board.system.article.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.article.domain.ArticleUpdateRequest
import com.ttasjwi.board.system.article.domain.ArticleUpdateResponse
import com.ttasjwi.board.system.article.domain.ArticleUpdateUseCase
import com.ttasjwi.board.system.article.test.base.ArticleRestDocsTest
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

@DisplayName("[article-web-adapter] ArticleUpdateController 테스트")
@WebMvcTest(ArticleUpdateController::class)
class ArticleUpdateControllerTest : ArticleRestDocsTest() {

    @MockkBean
    private lateinit var articleUpdateUseCase: ArticleUpdateUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}"
        val articleId = 1233151335L
        val request = ArticleUpdateRequest(
            title = "새 제목",
            content = "새 본문",
        )
        val accessToken = generateAccessToken(
            userId = 1557,
            role = Role.USER,
            issuedAt = appDateTimeFixture(),
            expiresAt = appDateTimeFixture(minute = 30)
        )
        val currentTime = appDateTimeFixture(minute = 18)
        changeCurrentTime(currentTime)

        val response = ArticleUpdateResponse(
            articleId = "1233151335",
            title = request.title!!,
            content = request.content!!,
            boardId = "131433451451",
            articleCategoryId = "1435135151",
            writerId = accessToken.authUser.userId.toString(),
            writerNickname = "땃쥐",
            createdAt = appDateTimeFixture(minute = 10).toZonedDateTime(),
            modifiedAt = currentTime.toZonedDateTime(),
        )

        every { articleUpdateUseCase.update(articleId, request) } returns response

        mockMvc
            .perform(
                request(HttpMethod.PUT, urlPattern, articleId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
                    .header("Authorization", "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.title").value(response.title),
                jsonPath("$.content").value(response.content),
                jsonPath("$.boardId").value(response.boardId),
                jsonPath("$.articleCategoryId").value(response.articleCategoryId),
                jsonPath("$.writerId").value(response.writerId),
                jsonPath("$.writerNickname").value(response.writerNickname),
                jsonPath("$.createdAt").value("2025-01-01T00:10:00+09:00"),
                jsonPath("$.modifiedAt").value("2025-01-01T00:18:00+09:00")
            )
            .andDocument(
                identifier = "article-update-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                            example "Bearer 액세스토큰",
                ),
                pathParameters(
                    "articleId"
                            paramMeans "게시글 식별자(Id)"
                ),
                requestBody(
                    "title"
                            type STRING
                            means "게시글 제목"
                            constraint "최대 50자. 공백만으로 구성되어선 안 됨.",
                    "content"
                            type STRING
                            means "게시판 본문"
                            constraint "최대 3000자.",
                ),
                responseBody(
                    "articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "title"
                            type STRING
                            means "게시글 제목",

                    "content"
                            type STRING
                            means "게시글 본문",
                    "boardId"
                            type STRING
                            means "게시글이 속한 게시판 식별자(Id)",
                    "articleCategoryId"
                            type STRING
                            means "게시글 카테고리 식별자(Id)",
                    "writerId"
                            type STRING
                            means "작성자 식별자(Id)",
                    "writerNickname"
                            type STRING
                            means "작성시점의 작성자 닉네임",
                    "createdAt"
                            type DATETIME
                            means "작성 시각",
                    "modifiedAt"
                            type DATETIME
                            means "마지막 수정 시각",
                )
            )
        verify(exactly = 1) { articleUpdateUseCase.update(articleId, request) }
    }

    @Test
    @DisplayName("미인증 사용자는 게시글을 수정할 수 없다.")
    fun testUnauthenticated() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}"
        val articleId = 1233151335L
        val request = ArticleUpdateRequest(
            title = "새 제목",
            content = "새 본문",
        )

        mockMvc
            .perform(
                request(HttpMethod.PUT, urlPattern, articleId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
            )
            .andExpectAll(
                status().isUnauthorized,
            )
    }
}
