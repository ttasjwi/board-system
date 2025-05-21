package com.ttasjwi.board.system.articlelike.domain.processor

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleLikeCancelCommand
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleLikeNotFoundException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikeCountPersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikePersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class ArticleLikeCancelProcessor(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleLikePersistencePort: ArticleLikePersistencePort,
    private val articleLikeCountPersistencePort: ArticleLikeCountPersistencePort,
) {

    @Transactional
    fun cancelLike(command: ArticleLikeCancelCommand) {
        // 게시글 존재여부 확인
        checkArticleExists(command.articleId)

        // 좋아요 존재 여부 확인
        checkArticleLikeExists(command.articleId, command.user.userId)

        // 좋아요 제거
        removeArticleLike(command.articleId, command.user.userId)

        // 좋아요수 감소, 수정 반영
        decreaseArticleLikeCount(command.articleId)
    }

    /**
     * 게시글 존재여부 확인
     */
    private fun checkArticleExists(articleId: Long) {
        if (!articlePersistencePort.existsByArticleId(articleId)) {
            throw ArticleNotFoundException(articleId = articleId)
        }
    }


    /**
     * 사용자가 게시글 좋아요를 했는지 확인
     */
    private fun checkArticleLikeExists(articleId: Long, userId: Long) {
        if (!articleLikePersistencePort.existsByArticleIdAndUserId(articleId, userId)) {
            throw ArticleLikeNotFoundException(articleId = articleId, userId = userId)
        }
    }

    /**
     * 게시글 좋아요 제거
     */
    private fun removeArticleLike(articleId: Long, userId: Long) {
        articleLikePersistencePort.remove(articleId, userId)
    }

    /**
     * 게시글 좋아요 수 감소
     */
    private fun decreaseArticleLikeCount(articleId: Long) {
        articleLikeCountPersistencePort.decrease(articleId)
    }
}
