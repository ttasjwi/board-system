package com.ttasjwi.board.system.board.domain.test.support

import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateUseCase
import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateUseCaseImpl
import com.ttasjwi.board.system.board.domain.BoardCreateUseCase
import com.ttasjwi.board.system.board.domain.BoardCreateUseCaseImpl
import com.ttasjwi.board.system.board.domain.mapper.BoardArticleCategoryCreateCommandMapper
import com.ttasjwi.board.system.board.domain.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.board.domain.policy.fixture.*
import com.ttasjwi.board.system.board.domain.port.fixture.BoardArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.board.domain.port.fixture.BoardPersistencePortFixture
import com.ttasjwi.board.system.board.domain.processor.BoardArticleCategoryCreateProcessor
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
    val boardArticleCategoryPersistencePortFixture: BoardArticleCategoryPersistencePortFixture by lazy { BoardArticleCategoryPersistencePortFixture() }

    // policy
    val boardNamePolicyFixture: BoardNamePolicyFixture by lazy { BoardNamePolicyFixture() }
    val boardDescriptionPolicyFixture: BoardDescriptionPolicyFixture by lazy { BoardDescriptionPolicyFixture() }
    val boardSlugPolicyFixture: BoardSlugPolicyFixture by lazy { BoardSlugPolicyFixture() }

    val boardArticleCategoryNamePolicyFixture: BoardArticleCategoryNamePolicyFixture by lazy { BoardArticleCategoryNamePolicyFixture() }
    val boardArticleCategorySlugPolicyFixture: BoardArticleCategorySlugPolicyFixture by lazy { BoardArticleCategorySlugPolicyFixture() }


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

    val boardArticleCategoryCreateCommandMapper : BoardArticleCategoryCreateCommandMapper by lazy {
        BoardArticleCategoryCreateCommandMapper(
            boardArticleCategoryNamePolicy = boardArticleCategoryNamePolicyFixture,
            boardArticleCategorySlugPolicy = boardArticleCategorySlugPolicyFixture,
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

    val boardArticleCategoryCreateProcessor: BoardArticleCategoryCreateProcessor by lazy {
        BoardArticleCategoryCreateProcessor(
            boardPersistencePort = boardPersistencePortFixture,
            boardArticleCategoryPersistencePort = boardArticleCategoryPersistencePortFixture,
        )
    }

    // usecase
    val boardCreateUseCase: BoardCreateUseCase by lazy {
        BoardCreateUseCaseImpl(
            commandMapper = boardCreateCommandMapper,
            processor = boardCreateProcessor,
        )
    }

    val boardArticleCategoryCreateUseCase: BoardArticleCategoryCreateUseCase by lazy {
        BoardArticleCategoryCreateUseCaseImpl(
            commandMapper = boardArticleCategoryCreateCommandMapper,
            processor = boardArticleCategoryCreateProcessor
        )
    }
}
