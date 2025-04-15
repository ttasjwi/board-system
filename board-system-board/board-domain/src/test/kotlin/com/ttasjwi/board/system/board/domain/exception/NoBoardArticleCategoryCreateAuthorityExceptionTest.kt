package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("NoBoardArticleCategoryCreateAuthorityException 테스트")
class NoBoardArticleCategoryCreateAuthorityExceptionTest {

    @Test
    @DisplayName("예외 값 테스트")
    fun test() {
        val boardManagerId = 12345L
        val creatorId = 565443L
        val exception = NoBoardArticleCategoryCreateAuthorityException(
            boardManagerId = boardManagerId,
            creatorId = creatorId,
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.FORBIDDEN)
        assertThat(exception.code).isEqualTo("Error.NoBoardArticleCategoryCreateAuthority")
        assertThat(exception.source).isEqualTo("boardArticleCreateAuthority")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("카테고리를 추가할 권한이 없습니다. 게시판의 관리자가 아닙니다. (boardManagerId = $boardManagerId, creatorId = $creatorId)")
    }
}
