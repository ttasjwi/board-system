package com.ttasjwi.board.system.articleview.domain.processor

import com.ttasjwi.board.system.articleview.domain.command.ArticleViewCountIncreaseCommand
import com.ttasjwi.board.system.articleview.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articleview.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articleview.domain.port.ArticleViewCountLockPersistencePort
import com.ttasjwi.board.system.articleview.domain.port.ArticleViewCountPersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import java.time.Duration

@ApplicationProcessor
internal class ArticleViewCountIncreaseProcessor(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleViewCountLockPersistencePort: ArticleViewCountLockPersistencePort,
    private val articleViewCountPersistencePort: ArticleViewCountPersistencePort,
) {

    companion object {
        private val TTL = Duration.ofMinutes(10)
    }

    fun increase(command: ArticleViewCountIncreaseCommand) {
        // 게시글 존재 여부 확인
        checkArticleExistence(command.articleId)

        // 게시글 조회수 락 획득, 획득 실패시 그냥 반환
        if (!articleViewCountLockPersistencePort.lock(command.articleId, command.user.userId, TTL)) {
            return
        }

        // 게시글 조회수 증가
        articleViewCountPersistencePort.increase(command.articleId)
    }

    private fun checkArticleExistence(articleId: Long) {
        if (!articlePersistencePort.existsById(articleId)) {
            throw ArticleNotFoundException(articleId = articleId)
        }
    }
}
