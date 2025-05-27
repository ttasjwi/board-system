package com.ttasjwi.board.system.articleread.domain

import java.time.ZonedDateTime

interface ArticleDetailReadUseCase {

    fun read(articleId: Long): ArticleDetailReadResponse
}

data class ArticleDetailReadResponse(
    val articleId: String, // 게시글 모듈에서 관리됨 (게시글 테이블)
    val title: String, // 게시글 모듈에서 관리됨(게시글 테이블)
    val content: String, // 게시글 모듈에서 관리됨(게시글 테이블)
    val board: Board, // 게시판 모듈에서 관리됨 (게시판 테이블)
    val articleCategory: ArticleCategory, // 게시판 모듈에서 관리됨 (게시글 카테고리 테이블)
    val writer: Writer, // 게시글 모듈에서 관리됨 (게시판 테이블)
    val viewCount: Long, // 조회수 모듈에서 관리됨 (Redis)
    val commentCount: Long, // 댓글 모듈에서 관리됨 (게시글 댓글수 테이블)
    val liked: Boolean, // 좋아요 모듈에서 관리됨 (게시글 좋아요 테이블)
    val likeCount: Long, // 좋아요 모듈에서 관리됨 (게시글 좋아요수 테이블)
    val disliked: Boolean, // 좋아요 모듈에서 관리됨 (게시글 싫어요 테이블)
    val dislikeCount: Long, // 좋아요 모듈에서 관리됨 (게시글 싫어요수 테이블)
    val createdAt: ZonedDateTime, // 게시글 모듈에서 관리됨 (게시글 테이블)
    val modifiedAt: ZonedDateTime, // 게시글 모듈에서 관리됨 (게시글 테이블)
) {

    data class Board(
        val boardId: String,
        val name: String,
        val slug: String,
    )

    data class ArticleCategory(
        val articleCategoryId: String,
        val name: String,
        val slug: String,
        val allowSelfDelete: Boolean,
        val allowLike: Boolean,
        val allowDislike: Boolean,
    )

    data class Writer(
        val writerId: String,
        val nickname: String, // 작성시점 닉네임
    )
}
