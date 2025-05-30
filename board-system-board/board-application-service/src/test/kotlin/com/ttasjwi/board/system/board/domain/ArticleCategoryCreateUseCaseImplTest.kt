package com.ttasjwi.board.system.board.domain

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.board.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.board.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[board-application-service] ArticleCategoryUseCaseImpl : 게시글 카테고리 생성 유즈케이스 구현체")
class ArticleCategoryCreateUseCaseImplTest {

    private lateinit var useCase: ArticleCategoryCreateUseCase
    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture
    private lateinit var savedBoard: Board
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        useCase = container.articleCategoryCreateUseCase
        articleCategoryPersistencePortFixture = container.articleCategoryPersistencePortFixture
        savedBoard = container.boardPersistencePortFixture.save(
            boardFixture(
                boardId = 1L,
                name = "동물",
                slug = "animal",
                description = "고양이 게시판입니다.",
                managerId = 1L,
                createdAt = appDateTimeFixture(minute = 5)
            )
        )
        currentTime = appDateTimeFixture(minute = 9)
        container.timeManagerFixture.changeCurrentTime(currentTime)

        container.authUserLoaderFixture.changeAuthUser(authUserFixture(savedBoard.managerId, Role.USER))
    }


    @Test
    @DisplayName("성공")
    fun test() {
        // given
        val boardId = savedBoard.boardId
        val request = ArticleCategoryCreateRequest(
            name = "일반",
            slug = "general",
            allowWrite = true,
            allowSelfEditDelete = true,
            allowComment = true,
            allowLike = true,
            allowDislike = true
        )
        // when
        val response = useCase.create(boardId, request)

        // then
        val findArticleCategory = articleCategoryPersistencePortFixture.findByIdOrNull(response.articleCategoryId.toLong())!!

        assertThat(response.articleCategoryId).isNotNull()
        assertThat(response.boardId).isEqualTo(boardId.toString())
        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.slug).isEqualTo(request.slug)
        assertThat(response.allowSelfEditDelete).isEqualTo(request.allowSelfEditDelete)
        assertThat(response.allowLike).isEqualTo(request.allowLike)
        assertThat(response.allowDislike).isEqualTo(request.allowDislike)
        assertThat(response.createdAt).isEqualTo(currentTime.toZonedDateTime())

        assertThat(findArticleCategory.articleCategoryId).isEqualTo(response.articleCategoryId.toLong())
    }
}
