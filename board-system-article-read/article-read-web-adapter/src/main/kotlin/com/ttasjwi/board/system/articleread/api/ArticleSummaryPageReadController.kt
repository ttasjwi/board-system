package com.ttasjwi.board.system.articleread.api

import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadRequest
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadResponse
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleSummaryPageReadController(
    private val articleSummaryPageReadUseCase: ArticleSummaryPageReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v2/boards/{boardId}/articles")
    fun readAllPage(
        @PathVariable boardId: Long,
        @RequestParam("page") page: Long?,
        @RequestParam("pageSize") pageSize: Long?
    ): ResponseEntity<ArticleSummaryPageReadResponse> {
        val request = ArticleSummaryPageReadRequest(boardId = boardId, page = page, pageSize = pageSize)
        val response = articleSummaryPageReadUseCase.readAllPage(request)
        return ResponseEntity.ok(response)
    }
}
