package com.ttasjwi.board.system.article.api

import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadRequest
import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadResponse
import com.ttasjwi.board.system.article.domain.ArticleInfiniteScrollReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleInfiniteScrollReadController(
    private val articleInfiniteScrollReadUseCase: ArticleInfiniteScrollReadUseCase,
) {

    @PermitAll
    @GetMapping("/api/v1/articles/infinite-scroll")
    fun readAllInfiniteScroll(@ModelAttribute request: ArticleInfiniteScrollReadRequest): ResponseEntity<ArticleInfiniteScrollReadResponse> {
        val response = articleInfiniteScrollReadUseCase.readAllInfiniteScroll(request)
        return ResponseEntity.ok(response)
    }
}
