package com.ttasjwi.board.system.article.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.article.domain.ArticleCreateRequest
import com.ttasjwi.board.system.article.domain.ArticleCreateResponse
import com.ttasjwi.board.system.article.domain.ArticleCreateUseCase
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

@DisplayName("[article-web-adapter] ArticleCreateController 테스트")
@WebMvcTest(ArticleCreateController::class)
class ArticleCreateControllerTest : ArticleRestDocsTest() {

    @MockkBean
    private lateinit var articleCreateUseCase: ArticleCreateUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/articles"
        val request = ArticleCreateRequest(
            title = "고양이 귀여워요",
            content = "근데 내가 더 귀여워요",
            articleCategoryId = 1234558
        )
        val accessToken = generateAccessToken(
            userId = 1557,
            role = Role.USER,
            issuedAt = appDateTimeFixture(),
            expiresAt = appDateTimeFixture(minute = 30)
        )
        val currentTime = appDateTimeFixture(minute = 18)
        changeCurrentTime(currentTime)

        val response = ArticleCreateResponse(
            articleId = "28383737373",
            title = request.title!!,
            content = request.content!!,
            boardId = "131433451451",
            articleCategoryId = request.articleCategoryId.toString(),
            writerId = accessToken.authUser.userId.toString(),
            writerNickname = "땃쥐",
            createdAt = currentTime.toZonedDateTime(),
            modifiedAt = currentTime.toZonedDateTime()
        )

        every { articleCreateUseCase.create(request) } returns response

        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
                    .header("Authorization", "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isCreated,
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.title").value(response.title),
                jsonPath("$.content").value(response.content),
                jsonPath("$.boardId").value(response.boardId),
                jsonPath("$.articleCategoryId").value(response.articleCategoryId),
                jsonPath("$.writerId").value(response.writerId),
                jsonPath("$.writerNickname").value(response.writerNickname),
                jsonPath("$.createdAt").value("2025-01-01T00:18:00+09:00"),
                jsonPath("$.modifiedAt").value("2025-01-01T00:18:00+09:00")
            )
            .andDocument(
                identifier = "article-create-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                            example "Bearer 액세스토큰",
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
                    "articleCategoryId"
                            type NUMBER
                            means "게시글 카테고리 식별자"
                            constraint "실제 게시글 카테고리가 존재해야함."
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
        verify(exactly = 1) { articleCreateUseCase.create(request) }
    }

    @Test
    @DisplayName("미인증 사용자는 게시글을 작성할 수 없다.")
    fun testUnauthenticated() {
        // given
        val url = "/api/v1/articles"
        val request = ArticleCreateRequest(
            title = "고양이 귀여워요",
            content = "근데 내가 더 귀여워요",
            articleCategoryId = 1234558
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
