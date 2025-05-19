package com.ttasjwi.board.system.articlelike.domain.mapper

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleLikeCreateCommand
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleLikeCreateCommandMapper(
    private val authUserLoader: AuthUserLoader,
    private val timeManager: TimeManager,
) {

    fun mapToCommand(articleId: Long): ArticleLikeCreateCommand {
        return ArticleLikeCreateCommand(
            articleId = articleId,
            likeUser = authUserLoader.loadCurrentAuthUser()!!,
            currentTime = timeManager.now()
        )
    }
}
