package com.ttasjwi.board.system.articlecomment.api

import com.ninjasquad.springmockk.MockkBean
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateRequest
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateUseCase
import com.ttasjwi.board.system.articlecomment.test.base.ArticleCommentRestDocsTest
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
@WebMvcTest(ArticleCommentCreateController::class)
class ArticleCommentCreateControllerTest : ArticleCommentRestDocsTest() {

    @MockkBean
    private lateinit var articleCommentCreateUseCase: ArticleCommentCreateUseCase

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val url = "/api/v1/article-comments"
        val request = ArticleCommentCreateRequest(
            content = "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",
            articleId = 1223455L,
            parentCommentId = 54432314L,
        )

        val accessToken = generateAccessToken(
            userId = 1557L,
            role = Role.USER,
            issuedAt = appDateTimeFixture(),
            expiresAt = appDateTimeFixture(minute = 30)
        )
        val currentTime = appDateTimeFixture(minute = 18)
        changeCurrentTime(currentTime)

        val response = ArticleCommentCreateResponse(
            articleCommentId = "1234134315135",
            content = request.content!!,
            articleId = request.articleId!!.toString(),
            rootParentCommentId = request.parentCommentId.toString(),
            writerId = accessToken.authUser.userId.toString(),
            writerNickname = "땃쥐",
            parentCommentWriterId = "834134134134",
            parentCommentWriterNickname = "땃고양이",
            deleteStatus = "NOT_DELETED",
            createdAt = currentTime.toZonedDateTime(),
            modifiedAt = currentTime.toZonedDateTime(),
        )

        every { articleCommentCreateUseCase.create(request) } returns response

        mockMvc
            .perform(
                request(HttpMethod.POST, url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeToJson(request))
                    .header("Authorization", "Bearer ${accessToken.tokenValue}")
            )
            .andExpectAll(
                status().isCreated,
                jsonPath("$.articleCommentId").value(response.articleCommentId),
                jsonPath("$.content").value(response.content),
                jsonPath("$.articleId").value(response.articleId),
                jsonPath("$.rootParentCommentId").value(response.rootParentCommentId),
                jsonPath("$.writerId").value(response.writerId),
                jsonPath("$.writerNickname").value(response.writerNickname),
                jsonPath("$.parentCommentWriterId").value(response.parentCommentWriterId),
                jsonPath("$.parentCommentWriterNickname").value(response.parentCommentWriterNickname),
                jsonPath("$.deleteStatus").value(response.deleteStatus),
                jsonPath("$.writerNickname").value(response.writerNickname),
                jsonPath("$.createdAt").value("2025-01-01T00:18:00+09:00"),
                jsonPath("$.modifiedAt").value("2025-01-01T00:18:00+09:00")
            )
            .andDocument(
                identifier = "article-comment-create-success",
                requestHeaders(
                    HttpHeaders.AUTHORIZATION
                            headerMeans "인증에 필요한 토큰('Bearer [액세스토큰]' 형태)"
                            example "Bearer 액세스토큰",
                ),
                requestBody(
                    "content"
                            type STRING
                            means "댓글 내용"
                            constraint "최대 3000자.",
                    "articleId"
                            type NUMBER
                            means "대상이 되는 댓글 Id"
                            constraint "삭제되지 않은, 실제 존재하는 게시글의 Id여야 함.",
                    "parentCommentId"
                            type NUMBER
                            means "(대댓글을 작성할 경우) 부모 댓글의 Id"
                            constraint "실제 존재하는 댓글의 Id 여야하고, 해당 게시글(articleId) 의 댓글이여야 함."
                            isOptional true,
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
                    "deleteStatus"
                            type STRING
                            means "댓글이 삭제됐는지, 삭제됐다면 어떤 사유로 삭제됐는지 나타내는 문자열(하단 참고)",
                    "createdAt"
                            type DATETIME
                            means "댓글 작성 시각",
                    "modifiedAt"
                            type DATETIME
                            means "댓글의 마지막 수정 시각",
                )
            )
        verify(exactly = 1) { articleCommentCreateUseCase.create(request) }
    }

    @Test
    @DisplayName("미인증 사용자는 댓글을 작성할 수 없다.")
    fun testUnauthenticated() {
        // given
        val url = "/api/v1/article-comments"
        val request = ArticleCommentCreateRequest(
            content = "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",
            articleId = 1223455L,
            parentCommentId = null,
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
