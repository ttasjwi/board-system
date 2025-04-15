package com.ttasjwi.board.system.board.domain.processor

import com.ttasjwi.board.system.board.domain.dto.BoardArticleCategoryCreateCommand
import com.ttasjwi.board.system.board.domain.exception.BoardNotFoundException
import com.ttasjwi.board.system.board.domain.exception.DuplicateBoardArticleCategoryNameException
import com.ttasjwi.board.system.board.domain.exception.DuplicateBoardArticleCategorySlugException
import com.ttasjwi.board.system.board.domain.exception.NoBoardArticleCategoryCreateAuthorityException
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardArticleCategory
import com.ttasjwi.board.system.board.domain.model.fixture.boardArticleCategoryFixture
import com.ttasjwi.board.system.board.domain.model.fixture.boardFixture
import com.ttasjwi.board.system.board.domain.port.fixture.BoardArticleCategoryPersistencePortFixture
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

@DisplayName("BoardArticleCategoryCreateProcessor 테스트")
class BoardArticleCategoryCreateProcessorTest {

    private lateinit var processor: BoardArticleCategoryCreateProcessor
    private lateinit var boardPersistencePortFixture: BoardPersistencePortFixture
    private lateinit var boardArticleCategoryPersistencePortFixture: BoardArticleCategoryPersistencePortFixture
    private lateinit var savedBoard: Board
    private lateinit var savedBoardArticleCategory: BoardArticleCategory

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        boardPersistencePortFixture = container.boardPersistencePortFixture
        boardArticleCategoryPersistencePortFixture = container.boardArticleCategoryPersistencePortFixture
        processor = container.boardArticleCategoryCreateProcessor

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
        savedBoardArticleCategory = boardArticleCategoryPersistencePortFixture.save(
            boardArticleCategoryFixture(
                boardArticleCategoryId = 1L,
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
        val command = BoardArticleCategoryCreateCommand(
            boardId = savedBoard.boardId,
            creator = authMemberFixture(memberId = savedBoard.managerId, role = Role.USER),
            boardArticleCategoryName = "질문",
            boardArticleCategorySlug = "question",
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )

        // when
        val boardArticleCategory = processor.create(command)

        // then
        val findBoardArticleCategory = boardArticleCategoryPersistencePortFixture.findByIdOrNull(boardArticleCategory.boardArticleCategoryId)!!

        assertThat(boardArticleCategory.boardArticleCategoryId).isNotNull()
        assertThat(boardArticleCategory.boardId).isEqualTo(command.boardId)
        assertThat(boardArticleCategory.name).isEqualTo(command.boardArticleCategoryName)
        assertThat(boardArticleCategory.slug).isEqualTo(command.boardArticleCategorySlug)
        assertThat(boardArticleCategory.allowSelfDelete).isEqualTo(command.allowSelfDelete)
        assertThat(boardArticleCategory.allowLike).isEqualTo(command.allowLike)
        assertThat(boardArticleCategory.allowDislike).isEqualTo(command.allowDislike)
        assertThat(boardArticleCategory.createdAt).isEqualTo(command.currentTime)

        assertThat(findBoardArticleCategory.boardArticleCategoryId).isEqualTo(boardArticleCategory.boardArticleCategoryId)
        assertThat(findBoardArticleCategory.boardId).isEqualTo(boardArticleCategory.boardId)
        assertThat(findBoardArticleCategory.name).isEqualTo(boardArticleCategory.name)
        assertThat(findBoardArticleCategory.slug).isEqualTo(boardArticleCategory.slug)
        assertThat(findBoardArticleCategory.allowSelfDelete).isEqualTo(boardArticleCategory.allowSelfDelete)
        assertThat(findBoardArticleCategory.allowLike).isEqualTo(boardArticleCategory.allowLike)
        assertThat(findBoardArticleCategory.allowDislike).isEqualTo(boardArticleCategory.allowDislike)
        assertThat(findBoardArticleCategory.createdAt).isEqualTo(boardArticleCategory.createdAt)
    }


    @Test
    @DisplayName("게시판 조회 실패 시, 예외 발생")
    fun testBoardNotFound() {
        // given
        val command = BoardArticleCategoryCreateCommand(
            boardId = 2L,
            creator = authMemberFixture(memberId = 1L, role = Role.USER),
            boardArticleCategoryName = "질문",
            boardArticleCategorySlug = "question",
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
        val command = BoardArticleCategoryCreateCommand(
            boardId = savedBoard.boardId,
            creator = authMemberFixture(memberId = 2L, role = Role.USER),
            boardArticleCategoryName = "질문",
            boardArticleCategorySlug = "question",
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )

        // when
        // then
        val ex = assertThrows<NoBoardArticleCategoryCreateAuthorityException> {
            processor.create(command)
        }
        assertThat(ex.message).isEqualTo("카테고리를 추가할 권한이 없습니다. 게시판의 관리자가 아닙니다. (boardManagerId = ${savedBoard.managerId}, creatorId = ${command.creator.memberId})")
    }


    @Test
    @DisplayName("게시물 카테고리명이 중복되면 예외 발생")
    fun testDuplicateCategoryName() {
        // given
        val command = BoardArticleCategoryCreateCommand(
            boardId = savedBoard.boardId,
            creator = authMemberFixture(memberId = 1L, role = Role.USER),
            boardArticleCategoryName = savedBoardArticleCategory.name,
            boardArticleCategorySlug = "gnr",
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )
        // when
        // then
        val ex = assertThrows<DuplicateBoardArticleCategoryNameException> {
            processor.create(command)
        }
        assertThat(ex.args).containsExactly(command.boardArticleCategoryName)
        assertThat(ex.debugMessage).isEqualTo("게시판 내에서 게시글 카테고리명이 중복됩니다. (boardArticleCategoryName = ${command.boardArticleCategoryName})")
    }

    @Test
    @DisplayName("게시물 카테고리 슬러그가 중복되면 예외 발생")
    fun testDuplicateCategorySlug() {
        // given
        val command = BoardArticleCategoryCreateCommand(
            boardId = savedBoard.boardId,
            creator = authMemberFixture(memberId = 1L, role = Role.USER),
            boardArticleCategoryName = "질문",
            boardArticleCategorySlug = savedBoardArticleCategory.slug,
            allowSelfDelete = false,
            allowLike = true,
            allowDislike = true,
            currentTime = appDateTimeFixture(minute = 8)
        )

        // when
        // then
        val ex = assertThrows<DuplicateBoardArticleCategorySlugException> {
            processor.create(command)
        }
        assertThat(ex.args).containsExactly(command.boardArticleCategorySlug)
        assertThat(ex.debugMessage).isEqualTo("게시판 내에서 게시글 카테고리 슬러그가 중복됩니다. (boardArticleCategorySlug = ${command.boardArticleCategorySlug})")
    }
}
