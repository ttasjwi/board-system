package com.ttasjwi.board.system.articlecomment.api

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentInfiniteScrollReadRequest
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentInfiniteScrollReadResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentInfiniteScrollReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleCommentInfiniteScrollReadController(
    private val useCase: ArticleCommentInfiniteScrollReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v1/article-comments/infinite-scroll")
    fun readAllInfiniteScroll(
        @ModelAttribute request: ArticleCommentInfiniteScrollReadRequest
    ): ResponseEntity<ArticleCommentInfiniteScrollReadResponse> {
        val response = useCase.readAllInfiniteScroll(request)
        return ResponseEntity.ok(response)
    }
}
