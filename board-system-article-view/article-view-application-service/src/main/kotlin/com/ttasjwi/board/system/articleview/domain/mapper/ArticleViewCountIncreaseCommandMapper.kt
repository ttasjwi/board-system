package com.ttasjwi.board.system.articleview.domain.mapper

import com.ttasjwi.board.system.articleview.domain.command.ArticleViewCountIncreaseCommand
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader

@ApplicationCommandMapper
internal class ArticleViewCountIncreaseCommandMapper(
    private val authUserLoader: AuthUserLoader,
) {

    fun mapToCommand(articleId: Long): ArticleViewCountIncreaseCommand {
        return ArticleViewCountIncreaseCommand(
            articleId = articleId,
            user = authUserLoader.loadCurrentAuthUser()!!,
        )
    }
}
