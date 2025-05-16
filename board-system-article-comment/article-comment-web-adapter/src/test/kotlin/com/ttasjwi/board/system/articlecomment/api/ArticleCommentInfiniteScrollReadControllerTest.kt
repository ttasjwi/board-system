package com.ttasjwi.board.system.articlecomment.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentInfiniteScrollReadRequest
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentInfiniteScrollReadResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentInfiniteScrollReadUseCase
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

@DisplayName("[article-comment-web-adapter] ArticleCommentInfiniteScrollReadController 테스트")
@WebMvcTest(ArticleCommentInfiniteScrollReadController::class)
class ArticleCommentInfiniteScrollReadControllerTest : ArticleCommentRestDocsTest() {

    @MockkBean
    private lateinit var articleCommentInfiniteScrollReadUseCase: ArticleCommentInfiniteScrollReadUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/article-comments/infinite-scroll"
        val articleId = 12345666L

        val request = ArticleCommentInfiniteScrollReadRequest(
            articleId = articleId,
            pageSize = 6L,
            lastRootParentCommentId = 2L,
            lastCommentId = 54L
        )

        // ------------------------------------
        // └ 55 (삭제됨)
        // └ 56
        // 3
        // └ 57
        // └ 58 (삭제됨)
        // └ 59
        // ------------------------------- 끝

        val response = ArticleCommentInfiniteScrollReadResponse(
            hasNext = true,
            comments = listOf(
                ArticleCommentInfiniteScrollReadResponse.CommentItem(
                    deleteStatus = "DELETED_BY_WRITER",
                    data = null,
                ),
                ArticleCommentInfiniteScrollReadResponse.CommentItem(
                    deleteStatus = "NOT_DELETED",
                    data = ArticleCommentInfiniteScrollReadResponse.CommentItem.Data(
                        articleCommentId = "56",
                        content = "2번 댓글에 대한 대댓글 (3)",
                        articleId = articleId.toString(),
                        rootParentCommentId = "2",
                        writerId = "56",
                        writerNickname = "56번작성자",
                        parentCommentWriterId = "2",
                        parentCommentWriterNickname = "2번작성자",
                        createdAt = appDateTimeFixture(minute = 8).toZonedDateTime(),
                        modifiedAt = appDateTimeFixture(minute = 9).toZonedDateTime()
                    )
                ),
                ArticleCommentInfiniteScrollReadResponse.CommentItem(
                    deleteStatus = "NOT_DELETED",
                    data = ArticleCommentInfiniteScrollReadResponse.CommentItem.Data(
                        articleCommentId = "3",
                        content = "3번 댓글",
                        articleId = articleId.toString(),
                        rootParentCommentId = "3",
                        writerId = "3",
                        writerNickname = "3번작성자",
                        parentCommentWriterId = null,
                        parentCommentWriterNickname = null,
                        createdAt = appDateTimeFixture(minute = 3).toZonedDateTime(),
                        modifiedAt = appDateTimeFixture(minute = 3).toZonedDateTime()
                    )
                ),
                ArticleCommentInfiniteScrollReadResponse.CommentItem(
                    deleteStatus = "NOT_DELETED",
                    data = ArticleCommentInfiniteScrollReadResponse.CommentItem.Data(
                        articleCommentId = "57",
                        content = "3번 댓글에 대한 대댓글 (1)",
                        articleId = articleId.toString(),
                        rootParentCommentId = "3",
                        writerId = "57",
                        writerNickname = "57번작성자",
                        parentCommentWriterId = "3",
                        parentCommentWriterNickname = "3번작성자",
                        createdAt = appDateTimeFixture(minute = 10).toZonedDateTime(),
                        modifiedAt = appDateTimeFixture(minute = 10).toZonedDateTime()
                    )
                ),
                ArticleCommentInfiniteScrollReadResponse.CommentItem(
                    deleteStatus = "DELETED_BY_WRITER",
                    data = null,
                ),
                ArticleCommentInfiniteScrollReadResponse.CommentItem(
                    deleteStatus = "NOT_DELETED",
                    data = ArticleCommentInfiniteScrollReadResponse.CommentItem.Data(
                        articleCommentId = "59",
                        content = "3번 댓글에 대한 대댓글 (3)",
                        articleId = articleId.toString(),
                        rootParentCommentId = "3",
                        writerId = "59",
                        writerNickname = "59번작성자",
                        parentCommentWriterId = "3",
                        parentCommentWriterNickname = "3번작성자",
                        createdAt = appDateTimeFixture(minute = 12).toZonedDateTime(),
                        modifiedAt = appDateTimeFixture(minute = 12).toZonedDateTime()
                    )
                ),
            )
        )

        every { articleCommentInfiniteScrollReadUseCase.readAllInfiniteScroll(request) } returns response

        // when
        mockMvc
            .perform(
                request(HttpMethod.GET, url)
                    .queryParams(
                        LinkedMultiValueMap<String, String?>().apply {
                            this["articleId"] = request.articleId.toString()
                            this["pageSize"] = request.pageSize.toString()
                            this["lastRootParentCommentId"] = request.lastRootParentCommentId.toString()
                            this["lastCommentId"] = request.lastCommentId.toString()
                        }
                    )
            )
            .andExpectAll(
                status().isOk,
                jsonPath("$.hasNext").value(response.hasNext),
                jsonPath("$.comments").isNotEmpty(),
                jsonPath("$.comments[1].deleteStatus").value(response.comments[1].deleteStatus),
                jsonPath("$.comments[1].data.articleCommentId").value(response.comments[1].data!!.articleCommentId),
                jsonPath("$.comments[1].data.content").value(response.comments[1].data!!.content),
                jsonPath("$.comments[1].data.articleId").value(response.comments[1].data!!.articleId),
                jsonPath("$.comments[1].data.rootParentCommentId").value(response.comments[1].data!!.rootParentCommentId),
                jsonPath("$.comments[1].data.writerId").value(response.comments[1].data!!.writerId),
                jsonPath("$.comments[1].data.writerNickname").value(response.comments[1].data!!.writerNickname),
                jsonPath("$.comments[1].data.parentCommentWriterId").value(response.comments[1].data!!.parentCommentWriterId),
                jsonPath("$.comments[1].data.parentCommentWriterNickname").value(response.comments[1].data!!.parentCommentWriterNickname),
                jsonPath("$.comments[1].data.createdAt").value("2025-01-01T00:08:00+09:00"),
                jsonPath("$.comments[1].data.modifiedAt").value("2025-01-01T00:09:00+09:00"),
            )
            .andDocument(
                identifier = "article-comment-infinite-scroll-read-success",
                queryParameters(
                    "articleId"
                            paramMeans "게시글 식별자(Id)",
                    "pageSize"
                            paramMeans "한 페이지 당 댓글 수"
                            constraint "1 이상 50 이하만 허용",
                    "lastRootParentCommentId"
                            paramMeans "기준이 되는 마지막 댓글의 루트 댓글 식별자. 있을 경우 이 다음부터, 없으면 처음부터 조회"
                            constraint "보낼 경우 lastCommentId 와 함께 보내야 제대로 된 결과를 받을 수 있음"
                            isOptional true,
                    "lastCommentId"
                            paramMeans "기준이 되는 마지막 댓글의 식별자. 있을 경우 이 다음부터, 없으면 처음부터 조회"
                            constraint "보낼 경우 lastRootParentCommentId 와 함께 보내야 제대로 된 결과를 받을 수 있음"
                            isOptional true
                ),
                responseBody(
                    "hasNext"
                            type BOOLEAN
                            means "다음 댓글이 존재하는 지 여부",
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
