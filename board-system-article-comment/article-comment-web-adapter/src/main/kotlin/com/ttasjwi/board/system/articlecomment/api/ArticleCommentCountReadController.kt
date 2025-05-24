package com.ttasjwi.board.system.articlecomment.api

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCountReadResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCountReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleCommentCountReadController(
    private val articleCommentCountReadUseCase: ArticleCommentCountReadUseCase
) {

    @PermitAll
    @GetMapping("/api/v1/articles/{articleId}/comments/count")
    fun readCommentCount(@PathVariable articleId: Long): ResponseEntity<ArticleCommentCountReadResponse> {
        val response = articleCommentCountReadUseCase.readCommentCount(articleId)
        return ResponseEntity.ok(response)
    }
}
