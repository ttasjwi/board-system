package com.ttasjwi.board.system.article.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollItem
import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadRequest
import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadResponse
import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadUseCase
import com.ttasjwi.board.system.article.test.base.ArticleRestDocsTest
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.util.*
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap

@DisplayName("[article-web-adapter] ArticleInfiniteScrollReadController 테스트")
@WebMvcTest(ArticleInfiniteScrollReadController::class)
class ArticleInfiniteScrollReadControllerTest : ArticleRestDocsTest() {

    @MockkBean
    private lateinit var articleInfiniteScrollReadUseCase: ArticleInfiniteScrollReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/articles/infinite-scroll"
        val boardId = 12345666L

        val request = ArticleInfiniteScrollReadRequest(
            boardId = boardId,
            pageSize = 3L,
            lastArticleId = 48L,
        )

        val response = ArticleInfiniteScrollReadResponse(
            hasNext = true,
            articles = listOf(
                ArticleInfiniteScrollItem(
                    articleId = "47",
                    title = "고양이 무서워하는 쥐들 참고해라",
                    content = "참고 하라고 ㅋㅋㅋㅋ",
                    boardId = boardId.toString(),
                    articleCategoryId = "12345",
                    writerId = "335",
                    writerNickname = "땃쥐",
                    createdAt = appDateTimeFixture(minute = 28).toZonedDateTime(),
                    modifiedAt = appDateTimeFixture(minute = 28).toZonedDateTime(),
                ),
                ArticleInfiniteScrollItem(
                    articleId = "46",
                    title = "윗글 쓴 사람 바보임 ↑↑↑↑↑↑↑↑",
                    content = "그러하다.",
                    boardId = boardId.toString(),
                    articleCategoryId = "12345",
                    writerId = "337",
                    writerNickname = "땃고양이",
                    createdAt = appDateTimeFixture(minute = 20).toZonedDateTime(),
                    modifiedAt = appDateTimeFixture(minute = 20).toZonedDateTime(),
                ),
                ArticleInfiniteScrollItem(
                    articleId = "45",
                    title = "오늘 점심 뭐먹냐",
                    content = "닭고기? 쥐고기?",
                    boardId = boardId.toString(),
                    articleCategoryId = "54321",
                    writerId = "3344",
                    writerNickname = "캬루",
                    createdAt = appDateTimeFixture(minute = 14).toZonedDateTime(),
                    modifiedAt = appDateTimeFixture(minute = 14).toZonedDateTime(),
                )
            )
        )

        every { articleInfiniteScrollReadUseCase.readAllInfiniteScroll(request) } returns response

        // when
        mockMvc
            .perform(
                request(HttpMethod.GET, url)
                    .queryParams(
                        LinkedMultiValueMap<String, String?>().apply {
                            this["boardId"] = request.boardId.toString()
                            this["pageSize"] = request.pageSize.toString()
                            this["lastArticleId"] = request.lastArticleId.toString()
                        }
                    )
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.hasNext").value(response.hasNext),
                jsonPath("$.articles").isNotEmpty(),
                jsonPath("$.articles[0].articleId").value(response.articles[0].articleId),
                jsonPath("$.articles[0].title").value(response.articles[0].title),
                jsonPath("$.articles[0].content").value(response.articles[0].content),
                jsonPath("$.articles[0].boardId").value(response.articles[0].boardId),
                jsonPath("$.articles[0].articleCategoryId").value(response.articles[0].articleCategoryId),
                jsonPath("$.articles[0].writerId").value(response.articles[0].writerId),
                jsonPath("$.articles[0].writerNickname").value(response.articles[0].writerNickname),
                jsonPath("$.articles[0].createdAt").value("2025-01-01T00:28:00+09:00"),
                jsonPath("$.articles[0].modifiedAt").value("2025-01-01T00:28:00+09:00"),
                jsonPath("$.articles[1].articleId").value(response.articles[1].articleId),
                jsonPath("$.articles[1].title").value(response.articles[1].title),
                jsonPath("$.articles[1].content").value(response.articles[1].content),
                jsonPath("$.articles[1].boardId").value(response.articles[1].boardId),
                jsonPath("$.articles[1].articleCategoryId").value(response.articles[1].articleCategoryId),
                jsonPath("$.articles[1].writerId").value(response.articles[1].writerId),
                jsonPath("$.articles[1].writerNickname").value(response.articles[1].writerNickname),
                jsonPath("$.articles[1].createdAt").value("2025-01-01T00:20:00+09:00"),
                jsonPath("$.articles[1].modifiedAt").value("2025-01-01T00:20:00+09:00"),
                jsonPath("$.articles[2].articleId").value(response.articles[2].articleId),
                jsonPath("$.articles[2].title").value(response.articles[2].title),
                jsonPath("$.articles[2].content").value(response.articles[2].content),
                jsonPath("$.articles[2].boardId").value(response.articles[2].boardId),
                jsonPath("$.articles[2].articleCategoryId").value(response.articles[2].articleCategoryId),
                jsonPath("$.articles[2].writerId").value(response.articles[2].writerId),
                jsonPath("$.articles[2].writerNickname").value(response.articles[2].writerNickname),
                jsonPath("$.articles[2].createdAt").value("2025-01-01T00:14:00+09:00"),
                jsonPath("$.articles[2].modifiedAt").value("2025-01-01T00:14:00+09:00"),
            )
            .andDocument(
                identifier = "article-infinite-scroll-read-success",
                queryParameters(
                    "boardId"
                            paramMeans "게시판 식별자(Id)",
                    "pageSize"
                            paramMeans "한 페이지 당 게시글 수"
                            constraint "1 이상 50 이하만 허용",
                    "lastArticleId"
                            paramMeans "기준이 되는 게시글 식별자. 있을 경우 이 다음부터, 없으면 처음부터 조회"
                            isOptional true,
                ),
                responseBody(
                    "hasNext"
                            type BOOLEAN
                            means "다음 게시글의 존재 여부 부울값",
                    "articles"
                            subSectionType "Article[]"
                            means "게시글 목록",
                    "articles[*].articleId"
                            type STRING
                            means "게시글 식별자(Id)",
                    "articles[*].title"
                            type STRING
                            means "게시글 제목",

                    "articles[*].content"
                            type STRING
                            means "게시글 본문",
                    "articles[*].boardId"
                            type STRING
                            means "게시글이 속한 게시판 식별자(Id)",
                    "articles[*].articleCategoryId"
                            type STRING
                            means "게시글 카테고리 식별자(Id)",
                    "articles[*].writerId"
                            type STRING
                            means "작성자 식별자(Id)",
                    "articles[*].writerNickname"
                            type STRING
                            means "작성시점의 작성자 닉네임",
                    "articles[*].createdAt"
                            type DATETIME
                            means "작성 시각",
                    "articles[*].modifiedAt"
                            type DATETIME
                            means "마지막 수정 시각",
                )
            )
    }
}
