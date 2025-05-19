package com.ttasjwi.board.system.articlelike.domain.mapper

import com.ttasjwi.board.system.articlelike.domain.dto.ArticleDislikeCancelCommand
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleDislikeCancelCommandMapper(
    private val authUserLoader: AuthUserLoader,
    private val timeManager: TimeManager,
) {

    fun mapToCommand(articleId: Long): ArticleDislikeCancelCommand {
        return ArticleDislikeCancelCommand(
            articleId = articleId,
            user = authUserLoader.loadCurrentAuthUser()!!,
            currentTime = timeManager.now()
        )
    }
}
