package com.ttasjwi.board.system.board.domain

import com.ttasjwi.board.system.board.domain.port.fixture.BoardPersistencePortFixture
import com.ttasjwi.board.system.board.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BoardCreateUseCaseImpl: 게시판 생성 요청을 받아 처리하는 유즈케이스 구현체")
class BoardCreateUseCaseImplTest {

    private lateinit var useCase: BoardCreateUseCase
    private lateinit var authMember: AuthMember
    private lateinit var currentTime: AppDateTime
    private lateinit var boardPersistencePortFixture: BoardPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()

        authMember = authMemberFixture(
            memberId = 1557L,
            role = Role.USER
        )
        container.authMemberLoaderFixture.changeAuthMember(authMember)
        currentTime = appDateTimeFixture(minute = 6)
        container.timeManagerFixture.changeCurrentTime(currentTime)
        boardPersistencePortFixture = container.boardPersistencePortFixture
        useCase = container.boardCreateUseCase
    }


    @Test
    @DisplayName("게시판 생성 애플리케이션 기능이 잘 동작해야한다.")
    fun test() {
        // given
        val request = BoardCreateRequest(
            name = "고양이",
            description = "고양이 게시판입니다.",
            slug = "cat"
        )

        // when
        val response = useCase.createBoard(request)

        // then
        val findBoard = boardPersistencePortFixture.findByIdOrNull(response.boardId.toLong())!!
        assertThat(response.boardId).isNotNull()
        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.description).isEqualTo(request.description)
        assertThat(response.managerId).isEqualTo(authMember.memberId.toString())
        assertThat(response.slug).isEqualTo(request.slug)
        assertThat(response.createdAt).isEqualTo(currentTime.toZonedDateTime())
        assertThat(findBoard).isNotNull
    }
}
