package com.ttasjwi.board.system.articlecomment.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadRequest
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadUseCase
import com.ttasjwi.board.system.articlecomment.test.base.ArticleCommentRestDocsTest
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

@DisplayName("[article-comment-web-adapter] ArticleCommentPageReadController 테스트")
@WebMvcTest(ArticleCommentPageReadController::class)
class ArticleCommentPageReadControllerTest : ArticleCommentRestDocsTest() {

    @MockkBean
    private lateinit var articleCommentPageReadUseCase: ArticleCommentPageReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/article-comments"
        val articleId = 12345666L

        val request = ArticleCommentPageReadRequest(
            articleId = articleId,
            page = 2L,
            pageSize = 5L,
        )

        val response = ArticleCommentPageReadResponse(
            page = request.page!!,
            pageSize = request.pageSize!!,
            pageGroupSize = 10,
            pageGroupStart = 1,
            pageGroupEnd = 10,
            hasNextPage = true,
            hasNextPageGroup = true,
            comments = listOf(
                ArticleCommentPageReadResponse.CommentItem(
                    deleteStatus = "NOT_DELETED",
                    data = ArticleCommentPageReadResponse.CommentItem.Data(
                        articleCommentId = "53",
                        content = "3번 댓글에 대한 대댓글",
                        articleId = articleId.toString(),
                        rootParentCommentId = "3",
                        writerId = "53",
                        writerNickname = "53번작성자",
                        parentCommentWriterId = "3",
                        parentCommentWriterNickname = "3번작성자",
                        createdAt = appDateTimeFixture(minute = 8).toZonedDateTime(),
                        modifiedAt = appDateTimeFixture(minute = 9).toZonedDateTime()
                    )
                ),
                ArticleCommentPageReadResponse.CommentItem(
                    deleteStatus = "DELETED_BY_WRITER",
                    data = null,
                ),
                ArticleCommentPageReadResponse.CommentItem(
                    deleteStatus = "NOT_DELETED",
                    data = ArticleCommentPageReadResponse.CommentItem.Data(
                        articleCommentId = "54",
                        content = "4번 댓글에 대한 대댓글",
                        articleId = articleId.toString(),
                        rootParentCommentId = "4",
                        writerId = "54",
                        writerNickname = "54번작성자",
                        parentCommentWriterId = "4",
                        parentCommentWriterNickname = "4번작성자",
                        createdAt = appDateTimeFixture(minute = 16).toZonedDateTime(),
                        modifiedAt = appDateTimeFixture(minute = 16).toZonedDateTime()
                    )
                ),
                ArticleCommentPageReadResponse.CommentItem(
                    deleteStatus = "NOT_DELETED",
                    data = ArticleCommentPageReadResponse.CommentItem.Data(
                        articleCommentId = "5",
                        content = "5번 댓글",
                        articleId = articleId.toString(),
                        rootParentCommentId = "5",
                        writerId = "5",
                        writerNickname = "5번작성자",
                        parentCommentWriterId = null,
                        parentCommentWriterNickname = null,
                        createdAt = appDateTimeFixture(minute = 23).toZonedDateTime(),
                        modifiedAt = appDateTimeFixture(minute = 23).toZonedDateTime()
                    )
                ),
                ArticleCommentPageReadResponse.CommentItem(
                    deleteStatus = "NOT_DELETED",
                    data = ArticleCommentPageReadResponse.CommentItem.Data(
                        articleCommentId = "55",
                        content = "5번 댓글에 대한 대댓글",
                        articleId = articleId.toString(),
                        rootParentCommentId = "5",
                        writerId = "55",
                        writerNickname = "55번작성자",
                        parentCommentWriterId = "5",
                        parentCommentWriterNickname = "5번작성자",
                        createdAt = appDateTimeFixture(minute = 29).toZonedDateTime(),
                        modifiedAt = appDateTimeFixture(minute = 29).toZonedDateTime()
                    )
                ),
            )
        )

        every { articleCommentPageReadUseCase.readAllPage(request) } returns response

        // when
        mockMvc
            .perform(
                request(HttpMethod.GET, url)
                    .queryParams(
                        LinkedMultiValueMap<String, String?>().apply {
                            this["articleId"] = request.articleId.toString()
                            this["page"] = request.page.toString()
                            this["pageSize"] = request.pageSize.toString()
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
                jsonPath("$.comments").isNotEmpty(),
                jsonPath("$.comments[0].deleteStatus").value(response.comments[0].deleteStatus),
                jsonPath("$.comments[0].data.articleCommentId").value(response.comments[0].data!!.articleCommentId),
                jsonPath("$.comments[0].data.content").value(response.comments[0].data!!.content),
                jsonPath("$.comments[0].data.articleId").value(response.comments[0].data!!.articleId),
                jsonPath("$.comments[0].data.rootParentCommentId").value(response.comments[0].data!!.rootParentCommentId),
                jsonPath("$.comments[0].data.writerId").value(response.comments[0].data!!.writerId),
                jsonPath("$.comments[0].data.writerNickname").value(response.comments[0].data!!.writerNickname),
                jsonPath("$.comments[0].data.parentCommentWriterId").value(response.comments[0].data!!.parentCommentWriterId),
                jsonPath("$.comments[0].data.parentCommentWriterNickname").value(response.comments[0].data!!.parentCommentWriterNickname),
                jsonPath("$.comments[0].data.createdAt").value("2025-01-01T00:08:00+09:00"),
                jsonPath("$.comments[0].data.modifiedAt").value("2025-01-01T00:09:00+09:00"),
            )
            .andDocument(
                identifier = "article-comment-page-read-success",
                queryParameters(
                    "articleId"
                            paramMeans "게시글 식별자(Id)",
                    "page"
                            paramMeans "페이지",
                    "pageSize"
                            paramMeans "한 페이지 당 댓글 수"
                            constraint "1 이상 50 이하만 허용"
                ),
                responseBody(
                    "page"
                            type NUMBER
                            means "현재 페이지",
                    "pageSize"
                            type NUMBER
                            means "한 페이지 당 댓글 수",
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
                    "comments"
                            subSectionType "Comment[]"
                            means "댓글 목록",
                    "comments[*].deleteStatus"
                            type STRING
                            means "댓글이 삭제됐는지, 삭제됐다면 어떤 사유로 삭제됐는지 나타내는 문자열(하단 참고)",
                    "comments[*].data"
                            subSectionType "Comment.Data"
                            means "댓글 데이터(삭제되지 않았을 때만 노출됨)"
                            isOptional true,
                    "comments[*].data.articleCommentId"
                            type STRING
                            means "게시글 댓글의 식별자(Id)",
                    "comments[*].data.content"
                            type STRING
                            means "게시글 댓글 내용",
                    "comments[*].data.articleId"
                            type STRING
                            means "게시글의 식별자(Id)",
                    "comments[*].data.rootParentCommentId"
                            type STRING
                            means "최상위 부모 댓글의 식별자(Id), 댓글 자신의 식별자와 이 값이 일치하면 루트 댓글",
                    "comments[*].data.writerId"
                            type STRING
                            means "댓글 작성자의 식별자(Id)",
                    "comments[*].data.writerNickname"
                            type STRING
                            means "댓글 작성시점의 작성자 닉네임",
                    "comments[*].data.parentCommentWriterId"
                            type STRING
                            means "(부모댓글이 있을 경우) 부모 댓글 작성자의 식별자(Id)"
                            isOptional true,
                    "comments[*].data.parentCommentWriterNickname"
                            type STRING
                            means "(부모댓글이 있을 경우) 부모 댓글 작성자의 댓글 작성시점 닉네임(Nickname)"
                            isOptional true,
                    "comments[*].data.createdAt"
                            type DATETIME
                            means "댓글 작성 시각",
                    "comments[*].data.modifiedAt"
                            type DATETIME
                            means "댓글의 마지막 수정 시각",
                )
            )
    }
}
