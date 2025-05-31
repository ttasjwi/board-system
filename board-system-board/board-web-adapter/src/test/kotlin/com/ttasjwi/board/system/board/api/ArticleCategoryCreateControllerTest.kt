package com.ttasjwi.board.system.board.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateRequest
import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateResponse
import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateUseCase
import com.ttasjwi.board.system.board.test.base.BoardRestDocsTest
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

@DisplayName("[board-web-adapter] ArticleCategoryCreateController 문서화 테스트")
@WebMvcTest(ArticleCategoryCreateController::class)
class ArticleCategoryCreateControllerTest : BoardRestDocsTest() {

    @MockkBean
    private lateinit var articleCategoryCreateUseCase: ArticleCategoryCreateUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/boards/{boardId}/article-categories"
        val boardId = 1L
        val request = ArticleCategoryCreateRequest(
            name = "일반",
            slug = "general",
            allowWrite = true,
            allowSelfEditDelete = true,
            allowComment = true,
            allowLike = true,
            allowDislike = true,
        )
        val accessToken = generateAccessToken(
            userId = 1557,
            role = Role.USER,
            issuedAt = appDateTimeFixture(),
            expiresAt = appDateTimeFixture(minute = 30)
        )
        changeCurrentTime(appDateTimeFixture(minute = 13))

        val response = ArticleCategoryCreateResponse(
            articleCategoryId = "12435346",
            boardId = boardId.toString(),
            name = request.name!!,
            slug = request.slug!!,
            allowWrite = request.allowWrite!!,
            allowSelfEditDelete = request.allowSelfEditDelete!!,
            allowComment = request.allowComment!!,
            allowLike = request.allowLike!!,
            allowDislike = request.allowDislike!!,
            createdAt = appDateTimeFixture(dayOfMonth = 1).toZonedDateTime(),
        )



        every { articleCategoryCreateUseCase.create(boardId, request) } returns response

        mockMvc
            .perform(
                request(HttpMethod.POST, urlPattern, boardId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
                    .header("Authorization", "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isCreated,
                jsonPath("$.articleCategoryId").value(response.articleCategoryId),
                jsonPath("$.boardId").value(response.boardId),
                jsonPath("$.name").value(response.name),
                jsonPath("$.slug").value(response.slug),
                jsonPath("$.allowWrite").value(response.allowWrite),
                jsonPath("$.allowSelfEditDelete").value(response.allowSelfEditDelete),
                jsonPath("$.allowComment").value(response.allowComment),
                jsonPath("$.allowLike").value(response.allowLike),
                jsonPath("$.allowDislike").value(response.allowLike),
                jsonPath("$.allowSelfEditDelete").value(response.allowDislike),
                jsonPath("$.createdAt").value("2025-01-01T00:00:00+09:00")
            )
            .andDocument(
                identifier = "article-category-create-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                ),
                pathParameters(
                    "boardId"
                            paramMeans "게시판 식별자(Id)"
                ),
                requestBody(
                    "name"
                            type STRING
                            means "게시글 카테고리 이름"
                            constraint "최소 1자, 최대 20자, 양끝 공백 허용 안 함.",
                    "slug"
                            type STRING
                            means "게시글 카테고리 슬러그(게시글 카테고리 구분 영문자)"
                            constraint "최소 1자, 최대 8자, 영어 대소문자 및 숫자만 허용",
                    "allowWrite"
                            type BOOLEAN
                            means "일반 사용자가 게시글 작성을 할 수 있는 지 여부",
                    "allowSelfEditDelete"
                            type BOOLEAN
                            means "작성자가 스스로 게시글 삭제 또는 수정을 할 수 있는 지 여부",
                    "allowComment"
                            type BOOLEAN
                            means "일반 사용자가 댓글을 작성할 수 있는 지 여부",
                    "allowLike"
                            type BOOLEAN
                            means "사용자들이 게시글을 좋아요 할 수 있는 지 여부",
                    "allowDislike"
                            type BOOLEAN
                            means "사용자들이 게시글을 싫어요 할 수 있는 지 여부",
                ),
                responseBody(
                    "articleCategoryId"
                            type STRING
                            means "게시글 카테고리 식별자(Id)",
                    "boardId"
                            type STRING
                            means "게시판 식별자(Id)",
                    "name"
                            type STRING
                            means "게시글 카테고리 이름",
                    "slug"
                            type STRING
                            means "게시글 카테고리 슬러그(게시글 카테고리 구분 영문자)",
                    "allowWrite"
                            type BOOLEAN
                            means "일반 사용자가 게시글 작성을 할 수 있는 지 여부",
                    "allowSelfEditDelete"
                            type BOOLEAN
                            means "작성자가 스스로 게시글 삭제 또는 수정을 할 수 있는 지 여부",
                    "allowComment"
                            type BOOLEAN
                            means "일반 사용자가 댓글을 작성할 수 있는 지 여부",
                    "allowLike"
                            type BOOLEAN
                            means "사용자들이 게시글을 좋아요 할 수 있는 지 여부",
                    "allowDislike"
                            type BOOLEAN
                            means "사용자들이 게시글을 싫어요 할 수 있는 지 여부",
                    "createdAt"
                            type DATETIME
                            means "게시글 카테고리 생성시점"
                )
            )
        verify(exactly = 1) { articleCategoryCreateUseCase.create(boardId, request) }
    }

    @Test
    @DisplayName("미인증 사용자는 게시판을 생성할 수 없다.")
    fun testUnauthenticated() {
        // given
        val urlPattern = "/api/v1/boards/{boardId}/article-categories"
        val boardId = 1L
        val request = ArticleCategoryCreateRequest(
            name = "일반",
            slug = "general",
            allowWrite = true,
            allowSelfEditDelete = true,
            allowComment = true,
            allowLike = true,
            allowDislike = true,
        )

        mockMvc
            .perform(
                request(HttpMethod.POST, urlPattern, boardId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
            )
            .andExpectAll(
                status().isUnauthorized,
            )
    }
}
