package com.ttasjwi.board.system.board.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.board.domain.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.BoardCreateResponse
import com.ttasjwi.board.system.board.domain.BoardCreateUseCase
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

@DisplayName("[board-web-adapter] BoardCreateController 문서화 테스트")
@WebMvcTest(BoardCreateController::class)
class BoardCreateControllerTest : BoardRestDocsTest() {

    @MockkBean
    private lateinit var boardCreateUseCase: BoardCreateUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/boards"
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = "cat"
        )
        val accessToken = generateAccessToken(
            userId = 1557,
            role = Role.USER,
            issuedAt = appDateTimeFixture(),
            expiresAt = appDateTimeFixture(minute = 30)
        )

        val response = BoardCreateResponse(
            boardId = "1",
            name = request.name!!,
            description = request.description!!,
            slug = request.slug!!,
            managerId = accessToken.authUser.userId.toString(),
            createdAt = appDateTimeFixture(minute = 6).toZonedDateTime()
        )


        changeCurrentTime(appDateTimeFixture(minute = 13))

        every { boardCreateUseCase.createBoard(request) } returns response

        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
                    .header("Authorization", "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isCreated,
                jsonPath("$.boardId").value(response.boardId),
                jsonPath("$.name").value(response.name),
                jsonPath("$.description").value(response.description),
                jsonPath("$.managerId").value(response.managerId),
                jsonPath("$.slug").value(response.slug),
                jsonPath("$.createdAt").value("2025-01-01T00:06:00+09:00")
            )
            .andDocument(
                identifier = "board-create-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                            example "Bearer 액세스토큰",
                ),
                requestBody(
                    "slug"
                            type STRING
                            means "게시판 슬러그(게시판 구분 영문자)"
                            constraint "최소 4자, 최대 20자. 영어 소문자와 숫자로만 구성되어야 함.",
                    "name"
                            type STRING
                            means "게시판 이름"
                            constraint "최소 2자, 최대 6자. 영어, 숫자, 완성형 한글, 스페이스, / 로만 구성되어야 함.",
                    "description"
                            type STRING
                            means "게시판 설명"
                            constraint "최대 100자",
                ),
                responseBody(
                    "boardId"
                            type STRING
                            means "게시판 식별자(Id)",
                    "slug"
                            type STRING
                            means "게시판 슬러그",

                    "name"
                            type STRING
                            means "게시판 이름",
                    "description"
                            type STRING
                            means "게시판 설명",
                    "managerId"
                            type STRING
                            means "게시판 관리자 식별자(Id)",
                    "createdAt"
                            type DATETIME
                            means "게시판 생성 시점"
                )
            )
        verify(exactly = 1) { boardCreateUseCase.createBoard(request) }
    }

    @Test
    @DisplayName("미인증 사용자는 게시판을 생성할 수 없다.")
    fun testUnauthenticated() {
        // given
        val url = "/api/v1/boards"
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = "cat"
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
