package com.ttasjwi.board.system.articlelike.domain.processor

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleDislikeCreateCommand
import com.ttasjwi.board.system.articlelike.domain.exception.*
import com.ttasjwi.board.system.articlelike.domain.model.*
import com.ttasjwi.board.system.articlelike.domain.port.*
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class ArticleDislikeCreateProcessor(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleCategoryPersistencePort: ArticleCategoryPersistencePort,
    private val articleDislikePersistencePort: ArticleDislikePersistencePort,
    private val articleDislikeCountPersistencePort: ArticleDislikeCountPersistencePort,
) {

    private val idGenerator: IdGenerator = IdGenerator.create()

    @Transactional
    fun dislike(command: ArticleDislikeCreateCommand): ArticleDislike {
        // 게시글 조회
        val article = getArticleOrThrow(command.articleId)

        // 게시글 카테고리 조회
        val articleCategory = getArticleCategoryOrThrow(article.articleCategoryId)

        // 카테고리 정책 확인 (싫어요 금지를 하진 않았는지...?)
        checkAllowDislike(command.articleId, articleCategory)

        // 싫어요 존재 확인 (이미 좋아요했다면, 예외 발생)
        checkDuplicateDislike(command.articleId, command.dislikeUser.userId)

        // 싫어요 생성 및 저장
        val articleDislike = createArticleDislikeAndSave(command)

        // 싫어요수 증가, 수정 반영
        upsertArticleDislikeCount(command.articleId)
        return articleDislike
    }

    private fun getArticleOrThrow(articleId: Long): Article {
        return articlePersistencePort.findByIdOrNull(articleId)
            ?: throw ArticleNotFoundException(articleId)
    }

    private fun getArticleCategoryOrThrow(articleCategoryId: Long): ArticleCategory {
        return articleCategoryPersistencePort.findByIdOrNull(articleCategoryId)
            ?: throw IllegalStateException("게시글 카테고리가 존재하지 않음 (articleCategoryId=$articleCategoryId)")
    }

    private fun checkAllowDislike(articleId: Long, articleCategory: ArticleCategory) {
        if (!articleCategory.allowDislike) {
            throw ArticleDislikeNotAllowedException(articleId, articleCategory.articleCategoryId)
        }
    }

    private fun checkDuplicateDislike(articleId: Long, userId: Long) {
        if (articleDislikePersistencePort.existsByArticleIdAndUserId(articleId, userId)) {
            throw ArticleAlreadyDislikedException(articleId, userId)
        }
    }


    private fun createArticleDislikeAndSave(command: ArticleDislikeCreateCommand): ArticleDislike {
        val articleDislike = ArticleDislike.create(
            articleDislikeId = idGenerator.nextId(),
            articleId = command.articleId,
            userId = command.dislikeUser.userId,
            currentTime = command.currentTime,
        )
        articleDislikePersistencePort.save(articleDislike)
        return articleDislike
    }


    private fun upsertArticleDislikeCount(articleId: Long) {
        val articleDislikeCount = articleDislikeCountPersistencePort.findByIdOrNull(articleId = articleId)
            ?: ArticleDislikeCount.init(articleId)
        articleDislikeCount.increase()

        // 동시성 문제가 발생할 여지가 있음.
        // A 트랜잭션이 싫어요수를 조회
        // B 트랜잭션이 싫어요수를 조회
        // A 트랜잭션이 조회한 싫어요수를 기반으로 싫어요수 증가 커밋
        // B 트랜잭션이 조회한 싫어요수를 기반으로 싫어요수 증가 커밋
        // 이렇게 동시 요청자수가 2명일 경우 싫어요수가  2 증가해야하지만, 실제로는 1 증가할 수 있는 것이다.
        // 동시요청자수가 100명, 1000명 늘어나게 될 경우 싫어요수의 정확도가 실제 싫어요 갯수와 많이 어긋나게 된다.
        articleDislikeCountPersistencePort.save(articleDislikeCount)
    }
}
