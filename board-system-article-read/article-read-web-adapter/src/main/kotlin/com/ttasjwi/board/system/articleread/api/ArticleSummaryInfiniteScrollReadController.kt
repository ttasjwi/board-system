package com.ttasjwi.board.system.articleread.api

import com.ttasjwi.board.system.articleread.domain.ArticleSummaryInfiniteScrollReadRequest
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryInfiniteScrollReadResponse
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryInfiniteScrollReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleSummaryInfiniteScrollReadController(
    private val useCase: ArticleSummaryInfiniteScrollReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v2/boards/{boardId}/articles/infinite-scroll")
    fun readAllInfiniteScroll(
        @PathVariable("boardId") boardId: Long,
        @RequestParam("pageSize") pageSize: Long?,
        @RequestParam("lastArticleId") lastArticleId: Long?
    ): ResponseEntity<ArticleSummaryInfiniteScrollReadResponse> {
        val request = ArticleSummaryInfiniteScrollReadRequest(boardId = boardId, pageSize = pageSize, lastArticleId = lastArticleId)
        val response = useCase.readAllInfiniteScroll(request)
        return ResponseEntity.ok(response)
    }
}
