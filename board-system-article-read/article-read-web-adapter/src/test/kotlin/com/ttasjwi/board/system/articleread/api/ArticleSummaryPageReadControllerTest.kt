package com.ttasjwi.board.system.articleread.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadRequest
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadResponse
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadUseCase
import com.ttasjwi.board.system.articleread.test.base.ArticleReadRestDocsTest
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
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
import org.springframework.util.LinkedMultiValueMap

@DisplayName("[article-read-web-adapter] ArticleSummaryPageReadController 테스트")
@WebMvcTest(ArticleSummaryPageReadController::class)
class ArticleSummaryPageReadControllerTest : ArticleReadRestDocsTest() {

    @MockkBean
    private lateinit var articleSummaryPageReadUseCase: ArticleSummaryPageReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v2/boards/{boardId}/articles"
        val boardId = 12345666L
        val page = 2L
        val pageSize = 3L

        val response = ArticleSummaryPageReadResponse(
            page = page,
            pageSize = pageSize,
            pageGroupSize = 10,
            pageGroupStart = 1,
            pageGroupEnd = 10,
            hasNextPage = true,
            hasNextPageGroup = true,
            articles = listOf(
                ArticleSummaryPageReadResponse.Article(
                    articleId = "47",
                    title = "고양이 무서워하는 쥐들 참고해라",
                    board = ArticleSummaryPageReadResponse.Article.Board(
                        boardId = boardId.toString(),
                        name = "동물",
                        slug = "animal"
                    ),
                    articleCategory = ArticleSummaryPageReadResponse.Article.ArticleCategory(
                        articleCategoryId = "12345",
                        name = "일반",
                        slug = "general"
                    ),
                    writer = ArticleSummaryPageReadResponse.Article.Writer(
                        writerId = "335",
                        nickname = "땃쥐"
                    ),
                    viewCount = 475L,
                    commentCount = 15L,
                    likeCount = 35L,
                    dislikeCount = 0L,
                    createdAt = appDateTimeFixture(minute = 28).toZonedDateTime(),
                ),
                ArticleSummaryPageReadResponse.Article(
                    articleId = "46",
                    title = "윗글 쓴 사람 바보임 ↑↑↑↑↑↑↑↑",
                    board = ArticleSummaryPageReadResponse.Article.Board(
                        boardId = boardId.toString(),
                        name = "동물",
                        slug = "animal"
                    ),
                    articleCategory = ArticleSummaryPageReadResponse.Article.ArticleCategory(
                        articleCategoryId = "12345",
                        name = "일반",
                        slug = "general"
                    ),
                    writer = ArticleSummaryPageReadResponse.Article.Writer(
                        writerId = "337",
                        nickname = "땃고양이"
                    ),
                    viewCount = 335L,
                    commentCount = 3L,
                    likeCount = 0L,
                    dislikeCount = 15L,
                    createdAt = appDateTimeFixture(minute = 20).toZonedDateTime(),
                ),
                ArticleSummaryPageReadResponse.Article(
                    articleId = "45",
                    title = "오늘 점심 뭐먹냐",
                    board = ArticleSummaryPageReadResponse.Article.Board(
                        boardId = boardId.toString(),
                        name = "동물",
                        slug = "animal"
                    ),
                    articleCategory = ArticleSummaryPageReadResponse.Article.ArticleCategory(
                        articleCategoryId = "54321",
                        name = "질문",
                        slug = "question"
                    ),
                    writer = ArticleSummaryPageReadResponse.Article.Writer(
                        writerId = "3344",
                        nickname = "캬루"
                    ),
                    viewCount = 555L,
                    commentCount = 13L,
                    likeCount = 0L,
                    dislikeCount = 0L,
                    createdAt = appDateTimeFixture(minute = 14).toZonedDateTime(),
                ),
            )
        )

        every {
            articleSummaryPageReadUseCase.readAllPage(
                ArticleSummaryPageReadRequest(boardId, page, pageSize)
            )
        } returns response

        // when
        mockMvc
            .perform(
                request(HttpMethod.GET, urlPattern, boardId)
                    .queryParams(
                        LinkedMultiValueMap<String, String?>().apply {
                            this["page"] = page.toString()
                            this["pageSize"] = pageSize.toString()
                        }
                    )
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.page").value(response.page),
                jsonPath("$.pageSize").value(response.pageSize),
                jsonPath("$.pageGroupSize").value(response.pageGroupSize),
                jsonPath("$.pageGroupStart").value(response.pageGroupStart),
                jsonPath("$.pageGroupEnd").value(response.pageGroupEnd),
                jsonPath("$.hasNextPage").value(response.hasNextPage),
                jsonPath("$.hasNextPageGroup").value(response.hasNextPageGroup),
                jsonPath("$.articles").isNotEmpty(),
                jsonPath("$.articles[0].articleId").value(response.articles[0].articleId),
                jsonPath("$.articles[0].title").value(response.articles[0].title),
                jsonPath("$.articles[0].board.boardId").value(response.articles[0].board.boardId),
                jsonPath("$.articles[0].board.name").value(response.articles[0].board.name),
                jsonPath("$.articles[0].board.slug").value(response.articles[0].board.slug),
                jsonPath("$.articles[0].articleCategory.articleCategoryId").value(response.articles[0].articleCategory.articleCategoryId),
                jsonPath("$.articles[0].articleCategory.name").value(response.articles[0].articleCategory.name),
                jsonPath("$.articles[0].articleCategory.slug").value(response.articles[0].articleCategory.slug),
                jsonPath("$.articles[0].writer.writerId").value(response.articles[0].writer.writerId),
                jsonPath("$.articles[0].writer.nickname").value(response.articles[0].writer.nickname),
                jsonPath("$.articles[0].viewCount").value(response.articles[0].viewCount),
                jsonPath("$.articles[0].commentCount").value(response.articles[0].commentCount),
                jsonPath("$.articles[0].likeCount").value(response.articles[0].likeCount),
                jsonPath("$.articles[0].dislikeCount").value(response.articles[0].dislikeCount),
                jsonPath("$.articles[0].createdAt").value("2025-01-01T00:28:00+09:00"),
            )
            .andDocument(
                identifier = "article-summary-page-read-success",
                pathParameters(
                    "boardId"
                            paramMeans "게시판 식별자(Id)",
                ),
                queryParameters(
                    "page"
                            paramMeans "페이지"
                            constraint "1 이상 100 이하만 허용(그 이후는 무한 스크롤 방식 조회를 사용하세요.)",
                    "pageSize"
                            paramMeans "한 페이지 당 게시글 수"
                            constraint "1 이상 50 이하만 허용"
                ),
                responseBody(
                    "page"
                            type NUMBER
                            means "현재 페이지",
                    "pageSize"
                            type NUMBER
                            means "한 페이지 당 게시글 수",
                    "pageGroupSize"
                            type NUMBER
                            means "페이지 그룹(예: 1-10페이지, 11-20페이지,...) 의 크기 단위",
                    "pageGroupStart"
                            type NUMBER
                            means "페이지 그룹의 시작페이지 번호(예: 21)",
                    "pageGroupEnd"
                            type NUMBER
                            means "페이지 그룹의 끝페이지 번호(예: 30)",
                    "hasNextPage"
                            type BOOLEAN
                            means "다음 페이지의 존재 여부 부울값",
                    "hasNextPageGroup"
                            type BOOLEAN
                            means "다음 페이지 그룹의 존재 여부 부울값",
                    "articles"
                            subSectionType "Article[]"
                            means "게시글 목록",
                    "articles[*].articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "articles[*].title"
                            type STRING
                            means "게시글 제목",
                    "articles[*].board"
                            subSectionType "Board"
                            means "게시글이 속한 게시판 정보",
                    "articles[*].board.boardId"
                            type STRING
                            means "게시글이 속한 게시판 식별자(Id)",
                    "articles[*].board.name"
                            type STRING
                            means "게시글이 속한 게시판 이름",
                    "articles[*].board.slug"
                            type STRING
                            means "게시글이 속한 게시판 슬러그(게시글 구분 영문자)",
                    "articles[*].articleCategory"
                            subSectionType "ArticleCategory"
                            means "게시글이 속한 카테고리",
                    "articles[*].articleCategory.articleCategoryId"
                            type STRING
                            means "게시글이 속한 게시글 카테고리 식별자(Id)",
                    "articles[*].articleCategory.name"
                            type STRING
                            means "게시글이 속한 게시글 카테고리 이름",
                    "articles[*].articleCategory.slug"
                            type STRING
                            means "게시글이 속한 게시글 카테고리 슬러그(게시글 카테고리 구분 영문자)",
                    "articles[*].writer"
                            subSectionType "Writer"
                            means "작성자 정보",
                    "articles[*].writer.writerId"
                            type STRING
                            means "작성자 식별자(Id)",
                    "articles[*].writer.nickname"
                            type STRING
                            means "작성시점의 작성자 닉네임",
                    "articles[*].viewCount"
                            type NUMBER
                            means "게시글의 조회수",
                    "articles[*].commentCount"
                            type NUMBER
                            means "게시글의 댓글수",
                    "articles[*].likeCount"
                            type NUMBER
                            means "게시글의 좋아요 수",
                    "articles[*].dislikeCount"
                            type NUMBER
                            means "게시글의 싫어요 수",
                    "articles[*].createdAt"
                            type DATETIME
                            means "작성 시각",
                )
            )

        verify(exactly = 1) {
            articleSummaryPageReadUseCase.readAllPage(
                ArticleSummaryPageReadRequest(boardId, page, pageSize)
            )
        }
    }
}
