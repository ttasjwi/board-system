package com.ttasjwi.board.system.article.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.article.domain.ArticleReadResponse
import com.ttasjwi.board.system.article.domain.ArticleReadUseCase
import com.ttasjwi.board.system.article.test.base.ArticleRestDocsTest
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

@DisplayName("[article-web-adapter] ArticleReadController 테스트")
@WebMvcTest(ArticleReadController::class)
class ArticleReadControllerTest : ArticleRestDocsTest() {

    @MockkBean
    private lateinit var articleReadUseCase: ArticleReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/articles/{articleId}"
        val articleId = 1245567L
        val response = ArticleReadResponse(
            articleId = articleId.toString(),
            title = "제목",
            content = "본문",
            boardId = "131433451451",
            articleCategoryId = "87364535667",
            writerId = "155557",
            writerNickname = "땃쥐",
            createdAt = appDateTimeFixture(minute = 5).toZonedDateTime(),
            modifiedAt = appDateTimeFixture(minute = 5).toZonedDateTime()
        )
        every { articleReadUseCase.read(articleId) } returns response

        // when
        mockMvc
            .perform(
                request(
                    HttpMethod.GET, urlPattern, articleId
                )
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
                jsonPath("$.createdAt").value("2025-01-01T00:05:00+09:00"),
                jsonPath("$.modifiedAt").value("2025-01-01T00:05:00+09:00"),
            )
            .andDocument(
                identifier = "article-read-success",
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
        verify(exactly = 1) { articleReadUseCase.read(articleId) }
    }
}
