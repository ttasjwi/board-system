package com.ttasjwi.board.system.articlelike.domain.processor

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleDislikeCancelCommand
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleDislikeNotFoundException
import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikeCountPersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikePersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class ArticleDislikeCancelProcessor(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleDislikePersistencePort: ArticleDislikePersistencePort,
    private val articleDislikeCountPersistencePort: ArticleDislikeCountPersistencePort,
) {

    @Transactional
    fun cancelDislike(command: ArticleDislikeCancelCommand) {
        // 게시글 존재여부 확인
        checkArticleExists(command.articleId)

        // 싫어요 존재 여부 확인
        checkArticleDislikeExists(command.articleId, command.user.userId)

        // 싫어요 제거
        removeArticleDislike(command.articleId, command.user.userId)

        // 싫어요수 감소, 수정 반영
        decreaseArticleDislikeCount(command.articleId)
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
     * 사용자가 게시글 싫어요를 했는지 확인
     */
    private fun checkArticleDislikeExists(articleId: Long, userId: Long) {
        if (!articleDislikePersistencePort.existsByArticleIdAndUserId(articleId, userId)) {
            throw ArticleDislikeNotFoundException(articleId = articleId, userId = userId)
        }
    }

    /**
     * 게시글 싫어요 제거
     */
    private fun removeArticleDislike(articleId: Long, userId: Long) {
        articleDislikePersistencePort.remove(articleId, userId)
    }

    /**
     * 게시글 싫어요 수 감소
     */
    private fun decreaseArticleDislikeCount(articleId: Long) {
        articleDislikeCountPersistencePort.decrease(articleId)
    }
}
