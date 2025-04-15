package com.ttasjwi.board.system.board.domain.test.support

import com.ttasjwi.board.system.board.domain.BoardCreateUseCase
import com.ttasjwi.board.system.board.domain.BoardCreateUseCaseImpl
import com.ttasjwi.board.system.board.domain.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.board.domain.policy.fixture.BoardDescriptionPolicyFixture
import com.ttasjwi.board.system.board.domain.policy.fixture.BoardNamePolicyFixture
import com.ttasjwi.board.system.board.domain.policy.fixture.BoardSlugPolicyFixture
import com.ttasjwi.board.system.board.domain.port.fixture.BoardPersistencePortFixture
import com.ttasjwi.board.system.board.domain.processor.BoardCreateProcessor
import com.ttasjwi.board.system.common.auth.fixture.AuthMemberLoaderFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture

internal class TestContainer private constructor() {

    companion object {

        fun create(): TestContainer {
            return TestContainer()
        }
    }

    val timeManagerFixture: TimeManagerFixture by lazy { TimeManagerFixture() }
    val authMemberLoaderFixture: AuthMemberLoaderFixture by lazy { AuthMemberLoaderFixture() }

    // port
    val boardPersistencePortFixture: BoardPersistencePortFixture by lazy { BoardPersistencePortFixture() }

    // policy
    val boardNamePolicyFixture: BoardNamePolicyFixture by lazy { BoardNamePolicyFixture() }
    val boardDescriptionPolicyFixture: BoardDescriptionPolicyFixture by lazy { BoardDescriptionPolicyFixture() }
    val boardSlugPolicyFixture: BoardSlugPolicyFixture by lazy { BoardSlugPolicyFixture() }

    // mapper
    val boardCreateCommandMapper: BoardCreateCommandMapper by lazy {
        BoardCreateCommandMapper(
            boardNamePolicy = boardNamePolicyFixture,
            boardDescriptionPolicy = boardDescriptionPolicyFixture,
            boardSlugPolicy = boardSlugPolicyFixture,
            authMemberLoader = authMemberLoaderFixture,
            timeManager = timeManagerFixture
        )
    }

    // processor
    val boardCreateProcessor: BoardCreateProcessor by lazy {
        BoardCreateProcessor(
            boardPersistencePort = boardPersistencePortFixture,
        )
    }

    // usecase
    val boardCreateUseCase: BoardCreateUseCase by lazy {
        BoardCreateUseCaseImpl(
            commandMapper = boardCreateCommandMapper,
            processor = boardCreateProcessor,
        )
    }
}
