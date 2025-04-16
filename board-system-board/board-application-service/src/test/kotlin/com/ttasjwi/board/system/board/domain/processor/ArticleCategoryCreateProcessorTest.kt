package com.ttasjwi.board.system.board.domain.processor

import com.ttasjwi.board.system.board.domain.dto.ArticleCategoryCreateCommand
import com.ttasjwi.board.system.board.domain.exception.BoardNotFoundException
import com.ttasjwi.board.system.board.domain.exception.DuplicateArticleCategoryNameException
import com.ttasjwi.board.system.board.domain.exception.DuplicateArticleCategorySlugException
import com.ttasjwi.board.system.board.domain.exception.NoArticleCategoryCreateAuthorityException
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.ArticleCategory
import com.ttasjwi.board.system.board.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.board.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.board.domain.port.fixture.BoardPersistencePortFixture
import com.ttasjwi.board.system.board.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("ArticleCategoryCreateProcessor 테스트")
class ArticleCategoryCreateProcessorTest {

    private lateinit var processor: ArticleCategoryCreateProcessor
    private lateinit var boardPersistencePortFixture: BoardPersistencePortFixture
    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture
    private lateinit var savedBoard: Board
    private lateinit var savedArticleCategory: ArticleCategory

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        boardPersistencePortFixture = container.boardPersistencePortFixture
        articleCategoryPersistencePortFixture = container.articleCategoryPersistencePortFixture
        processor = container.articleCategoryCreateProcessor

        savedBoard = boardPersistencePortFixture.save(
            boardFixture(
                boardId = 1L,
                name = "동물",
                slug = "animal",
                description = "고양이 게시판입니다.",
                managerId = 1L,
                createdAt = appDateTimeFixture(minute = 5)
            )
        )
        savedArticleCategory = articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = 1L,
                name = "일반",
                slug = "general",
                boardId = savedBoard.boardId,
                allowSelfDelete = true,
                allowLike = true,
                allowDislike = true
            )
        )
    }


    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        // given
        val command = ArticleCategoryCreateCommand(
            boardId = savedBoard.boardId,
            creator = authMemberFixture(memberId = savedBoard.managerId, role = Role.USER),
            name = "질문",
            slug = "question",
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )

        // when
        val articleCategory = processor.create(command)

        // then
        val findArticleCategory = articleCategoryPersistencePortFixture.findByIdOrNull(articleCategory.articleCategoryId)!!

        assertThat(articleCategory.articleCategoryId).isNotNull()
        assertThat(articleCategory.boardId).isEqualTo(command.boardId)
        assertThat(articleCategory.name).isEqualTo(command.name)
        assertThat(articleCategory.slug).isEqualTo(command.slug)
        assertThat(articleCategory.allowSelfDelete).isEqualTo(command.allowSelfDelete)
        assertThat(articleCategory.allowLike).isEqualTo(command.allowLike)
        assertThat(articleCategory.allowDislike).isEqualTo(command.allowDislike)
        assertThat(articleCategory.createdAt).isEqualTo(command.currentTime)

        assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
        assertThat(findArticleCategory.boardId).isEqualTo(articleCategory.boardId)
        assertThat(findArticleCategory.name).isEqualTo(articleCategory.name)
        assertThat(findArticleCategory.slug).isEqualTo(articleCategory.slug)
        assertThat(findArticleCategory.allowSelfDelete).isEqualTo(articleCategory.allowSelfDelete)
        assertThat(findArticleCategory.allowLike).isEqualTo(articleCategory.allowLike)
        assertThat(findArticleCategory.allowDislike).isEqualTo(articleCategory.allowDislike)
        assertThat(findArticleCategory.createdAt).isEqualTo(articleCategory.createdAt)
    }


    @Test
    @DisplayName("게시판 조회 실패 시, 예외 발생")
    fun testBoardNotFound() {
        // given
        val command = ArticleCategoryCreateCommand(
            boardId = 2L,
            creator = authMemberFixture(memberId = 1L, role = Role.USER),
            name = "질문",
            slug = "question",
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )
        // when
        // then
        val ex = assertThrows<BoardNotFoundException> {
            processor.create(command)
        }

        assertThat(ex).isInstanceOf(BoardNotFoundException::class.java)
        assertThat(ex.source).isEqualTo("boardId")
        assertThat(ex.args).containsExactly("boardId", command.boardId)
    }


    @Test
    @DisplayName("게시물 카테고리 생성 권한 없을 때 예외 발생")
    fun testNoAuthority() {
        // given
        val command = ArticleCategoryCreateCommand(
            boardId = savedBoard.boardId,
            creator = authMemberFixture(memberId = 2L, role = Role.USER),
            name = "질문",
            slug = "question",
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )

        // when
        // then
        val ex = assertThrows<NoArticleCategoryCreateAuthorityException> {
            processor.create(command)
        }
        assertThat(ex.message).isEqualTo("카테고리를 추가할 권한이 없습니다. 게시판의 관리자가 아닙니다. (boardManagerId = ${savedBoard.managerId}, creatorId = ${command.creator.memberId})")
    }


    @Test
    @DisplayName("게시물 카테고리명이 중복되면 예외 발생")
    fun testDuplicateCategoryName() {
        // given
        val command = ArticleCategoryCreateCommand(
            boardId = savedBoard.boardId,
            creator = authMemberFixture(memberId = 1L, role = Role.USER),
            name = savedArticleCategory.name,
            slug = "gnr",
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )
        // when
        // then
        val ex = assertThrows<DuplicateArticleCategoryNameException> {
            processor.create(command)
        }
        assertThat(ex.args).containsExactly(command.name)
        assertThat(ex.debugMessage).isEqualTo("게시판 내에서 게시글 카테고리명이 중복됩니다. (articleCategoryName = ${command.name})")
    }

    @Test
    @DisplayName("게시물 카테고리 슬러그가 중복되면 예외 발생")
    fun testDuplicateCategorySlug() {
        // given
        val command = ArticleCategoryCreateCommand(
            boardId = savedBoard.boardId,
            creator = authMemberFixture(memberId = 1L, role = Role.USER),
            name = "질문",
            slug = savedArticleCategory.slug,
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )

        // when
        // then
        val ex = assertThrows<DuplicateArticleCategorySlugException> {
            processor.create(command)
        }
        assertThat(ex.args).containsExactly(command.slug)
        assertThat(ex.debugMessage).isEqualTo("게시판 내에서 게시글 카테고리 슬러그가 중복됩니다. (articleCategorySlug = ${command.slug})")
    }
}
