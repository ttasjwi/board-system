package com.ttasjwi.board.system.articlecomment.api

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentReadResponse
import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentReadUseCase
import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleCommentReadController(
    private val articleCommentReadUseCase: ArticleCommentReadUseCase,
) {

    @PermitAll
    @GetMapping("/api/v1/article-comments/{commentId}")
    fun read(@PathVariable("commentId") commentId: Long): ResponseEntity<ArticleCommentReadResponse> {
        val response = articleCommentReadUseCase.read(commentId)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}
