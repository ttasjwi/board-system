package com.ttasjwi.board.system.articlelike.domain.mapper

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleDislikeCreateCommand
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleDislikeCreateCommandMapper(
    private val authUserLoader: AuthUserLoader,
    private val timeManager: TimeManager,
) {

    fun mapToCommand(articleId: Long): ArticleDislikeCreateCommand {
        return ArticleDislikeCreateCommand(
            articleId = articleId,
            dislikeUser = authUserLoader.loadCurrentAuthUser()!!,
            currentTime = timeManager.now()
        )
    }
}
