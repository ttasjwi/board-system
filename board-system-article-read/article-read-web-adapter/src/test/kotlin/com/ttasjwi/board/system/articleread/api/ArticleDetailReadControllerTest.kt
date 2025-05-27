package com.ttasjwi.board.system.articleread.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articleread.domain.ArticleDetailReadResponse
import com.ttasjwi.board.system.articleread.domain.ArticleDetailReadUseCase
import com.ttasjwi.board.system.articleread.test.base.ArticleReadRestDocsTest
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

@DisplayName("[article-read-web-adapter] ArticleDetailReadController 테스트")
@WebMvcTest(ArticleDetailReadController::class)
class ArticleDetailReadControllerTest : ArticleReadRestDocsTest() {

    @MockkBean
    private lateinit var articleDetailReadUseCase: ArticleDetailReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v2/articles/{articleId}"
        val articleId = 1245567L
        val accessToken = generateAccessToken(
            userId = 15L,
            role = Role.USER,
            issuedAt = appDateTimeFixture(minute = 0),
            expiresAt = appDateTimeFixture(minute = 30),
        )
        changeCurrentTime(appDateTimeFixture(minute = 15))


        val response = ArticleDetailReadResponse(
            articleId = articleId.toString(),
            title = "제목",
            content = "본문",
            articleCategory = ArticleDetailReadResponse.ArticleCategory(
                articleCategoryId = "87364535667",
                name = "일반",
                slug = "general",
                allowSelfDelete = true,
                allowLike = true,
                allowDislike = true
            ),
            board = ArticleDetailReadResponse.Board(
                boardId = "131433451451",
                name = "고양이",
                slug = "cat"
            ),
            writer = ArticleDetailReadResponse.Writer(
                writerId = "155557",
                nickname = "땃쥐"
            ),
            viewCount = 8844L,
            commentCount = 15L,
            liked = true,
            likeCount = 14L,
            disliked = false,
            dislikeCount = 14L,
            createdAt = appDateTimeFixture(minute = 5).toZonedDateTime(),
            modifiedAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )
        every { articleDetailReadUseCase.read(articleId) } returns response

        // when
        mockMvc
            .perform(
                request(
                    HttpMethod.GET, urlPattern, articleId
                )
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.title").value(response.title),
                jsonPath("$.content").value(response.content),
                jsonPath("$.board.boardId").value(response.board.boardId),
                jsonPath("$.board.name").value(response.board.name),
                jsonPath("$.board.slug").value(response.board.slug),
                jsonPath("$.articleCategory.articleCategoryId").value(response.articleCategory.articleCategoryId),
                jsonPath("$.articleCategory.name").value(response.articleCategory.name),
                jsonPath("$.articleCategory.slug").value(response.articleCategory.slug),
                jsonPath("$.articleCategory.name").value(response.articleCategory.name),
                jsonPath("$.articleCategory.allowSelfDelete").value(response.articleCategory.allowSelfDelete),
                jsonPath("$.articleCategory.allowLike").value(response.articleCategory.allowLike),
                jsonPath("$.articleCategory.allowDislike").value(response.articleCategory.allowDislike),
                jsonPath("$.writer.writerId").value(response.writer.writerId),
                jsonPath("$.writer.nickname").value(response.writer.nickname),
                jsonPath("$.viewCount").value(response.viewCount),
                jsonPath("$.commentCount").value(response.commentCount),
                jsonPath("$.liked").value(response.liked),
                jsonPath("$.likeCount").value(response.likeCount),
                jsonPath("$.disliked").value(response.disliked),
                jsonPath("$.dislikeCount").value(response.dislikeCount),
                jsonPath("$.createdAt").value("2025-01-01T00:05:00+09:00"),
                jsonPath("$.modifiedAt").value("2025-01-01T00:05:00+09:00"),
            )
            .andDocument(
                identifier = "article-detail-read-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                            example "Bearer 액세스토큰"
                            isOptional true,
                ),
                pathParameters(
                    "articleId"
                            paramMeans "게시글 식별자(Id)"
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
                    "board"
                            subSectionType "Board"
                            means "게시글이 속한 게시판 정보",
                    "board.boardId"
                            type STRING
                            means "게시글이 속한 게시판 식별자(Id)",
                    "board.name"
                            type STRING
                            means "게시글이 속한 게시판 이름",
                    "board.slug"
                            type STRING
                            means "게시글이 속한 게시판 슬러그(게시글 구분 영문자)",
                    "articleCategory"
                            subSectionType "ArticleCategory"
                            means "게시글이 속한 카테고리",
                    "articleCategory.articleCategoryId"
                            type STRING
                            means "게시글이 속한 게시글 카테고리 식별자(Id)",
                    "articleCategory.name"
                            type STRING
                            means "게시글이 속한 게시글 카테고리 이름",
                    "articleCategory.slug"
                            type STRING
                            means "게시글이 속한 게시글 카테고리 슬러그(게시글 카테고리 구분 영문자)",
                    "articleCategory.allowSelfDelete"
                            type BOOLEAN
                            means "작성자가 스스로 게시글 삭제 또는 수정을 할 수 있는 지 여부",
                    "articleCategory.allowLike"
                            type BOOLEAN
                            means "사용자들이 게시글을 좋아요 할 수 있는 지 여부",
                    "articleCategory.allowDislike"
                            type BOOLEAN
                            means "사용자들이 게시글을 싫어요 할 수 있는 지 여부",
                    "writer"
                            subSectionType "Writer"
                            means "작성자 정보",
                    "writer.writerId"
                            type STRING
                            means "작성자 식별자(Id)",
                    "writer.nickname"
                            type STRING
                            means "작성시점의 작성자 닉네임",
                    "viewCount"
                            type NUMBER
                            means "게시글의 조회수",
                    "commentCount"
                            type NUMBER
                            means "게시글의 댓글수",
                    "liked"
                            type BOOLEAN
                            means "조회자가 게시글을 좋아요 했는지 여부(미인증 사용자는 false)",
                    "likeCount"
                            type NUMBER
                            means "게시글의 좋아요 수",
                    "disliked"
                            type BOOLEAN
                            means "조회자가 게시글을 싫어요 했는지 여부(미인증 사용자는 false)",
                    "dislikeCount"
                            type NUMBER
                            means "게시글의 싫어요 수",
                    "createdAt"
                            type DATETIME
                            means "작성 시각",
                    "modifiedAt"
                            type DATETIME
                            means "마지막 수정 시각",
                )
            )
        verify(exactly = 1) { articleDetailReadUseCase.read(articleId) }
    }
}
