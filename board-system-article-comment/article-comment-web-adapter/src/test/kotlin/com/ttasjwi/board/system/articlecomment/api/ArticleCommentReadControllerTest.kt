package com.ttasjwi.board.system.articlecomment.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentReadResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentReadUseCase
import com.ttasjwi.board.system.articlecomment.test.base.ArticleCommentRestDocsTest
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

@DisplayName("[article-comment-web-adapter] ArticleCommentReadController 테스트")
@WebMvcTest(ArticleCommentReadController::class)
class ArticleCommentReadControllerTest : ArticleCommentRestDocsTest() {

    @MockkBean
    private lateinit var articleCommentReadUseCase: ArticleCommentReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val urlPattern = "/api/v1/article-comments/{commentId}"
        val commentId = 12345L

        val response = ArticleCommentReadResponse(
            articleCommentId = commentId.toString(),
            content = "이게 뭐얔ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",
            articleId = "12134315",
            rootParentCommentId = "1111",
            writerId = "1",
            writerNickname = "땃쥐",
            parentCommentWriterId = "3",
            parentCommentWriterNickname = "땃고양이",
            createdAt = appDateTimeFixture(minute = 12).toZonedDateTime(),
            modifiedAt = appDateTimeFixture(minute = 12).toZonedDateTime(),
        )

        every { articleCommentReadUseCase.read(commentId) } returns response

        mockMvc
            .perform(
                request(HttpMethod.GET, urlPattern, commentId)
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.articleCommentId").value(response.articleCommentId),
                jsonPath("$.content").value(response.content),
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.rootParentCommentId").value(response.rootParentCommentId),
                jsonPath("$.writerId").value(response.writerId),
                jsonPath("$.writerNickname").value(response.writerNickname),
                jsonPath("$.parentCommentWriterId").value(response.parentCommentWriterId),
                jsonPath("$.parentCommentWriterNickname").value(response.parentCommentWriterNickname),
                jsonPath("$.writerNickname").value(response.writerNickname),
                jsonPath("$.createdAt").value("2025-01-01T00:12:00+09:00"),
                jsonPath("$.modifiedAt").value("2025-01-01T00:12:00+09:00")
            )
            .andDocument(
                identifier = "article-comment-read-success",
                pathParameters(
                    "commentId"
                            paramMeans "게시글 댓글의 식별자(Id)"
                ),
                responseBody(
                    "articleCommentId"
                            type STRING
                            means "게시글 댓글의 식별자(Id)",
                    "content"
                            type STRING
                            means "게시글 댓글 내용",
                    "articleId"
                            type STRING
                            means "게시글의 식별자(Id)",
                    "rootParentCommentId"
                            type STRING
                            means "최상위 부모 댓글의 식별자(Id), 댓글 자신의 식별자와 이 값이 일치하면 루트 댓글",
                    "writerId"
                            type STRING
                            means "댓글 작성자의 식별자(Id)",
                    "writerNickname"
                            type STRING
                            means "댓글 작성시점의 작성자 닉네임",
                    "parentCommentWriterId"
                            type STRING
                            means "(부모댓글이 있을 경우) 부모 댓글 작성자의 식별자(Id)"
                            isOptional true,
                    "parentCommentWriterNickname"
                            type STRING
                            means "(부모댓글이 있을 경우) 부모 댓글 작성자의 댓글 작성시점 닉네임(Nickname)"
                            isOptional true,
                    "createdAt"
                            type DATETIME
                            means "댓글 작성 시각",
                    "modifiedAt"
                            type DATETIME
                            means "댓글의 마지막 수정 시각",
                )
            )
        verify(exactly = 1) { articleCommentReadUseCase.read(commentId) }
    }
}
