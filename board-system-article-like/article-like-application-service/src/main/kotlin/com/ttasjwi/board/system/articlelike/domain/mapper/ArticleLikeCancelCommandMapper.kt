package com.ttasjwi.board.system.articlelike.domain.mapper

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleLikeCancelCommand
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleLikeCancelCommandMapper(
    private val authUserLoader: AuthUserLoader,
    private val timeManager: TimeManager,
) {

    fun mapToCommand(articleId: Long): ArticleLikeCancelCommand {
        return ArticleLikeCancelCommand(
            articleId = articleId,
            user = authUserLoader.loadCurrentAuthUser()!!,
            currentTime = timeManager.now()
        )
    }
}
