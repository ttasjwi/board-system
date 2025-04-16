package com.ttasjwi.board.system.board.domain.test.support

import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateUseCase
import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateUseCaseImpl
import com.ttasjwi.board.system.board.domain.BoardCreateUseCase
import com.ttasjwi.board.system.board.domain.BoardCreateUseCaseImpl
import com.ttasjwi.board.system.board.domain.mapper.ArticleCategoryCreateCommandMapper
import com.ttasjwi.board.system.board.domain.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.board.domain.policy.fixture.*
import com.ttasjwi.board.system.board.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.board.domain.port.fixture.BoardPersistencePortFixture
import com.ttasjwi.board.system.board.domain.processor.ArticleCategoryCreateProcessor
import com.ttasjwi.board.system.board.domain.processor.BoardCreateProcessor
import com.ttasjwi.board.system.common.auth.fixture.AuthUserLoaderFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture

internal class TestContainer private constructor() {

    companion object {

        fun create(): TestContainer {
            return TestContainer()
        }
    }

    val timeManagerFixture: TimeManagerFixture by lazy { TimeManagerFixture() }
    val authUserLoaderFixture: AuthUserLoaderFixture by lazy { AuthUserLoaderFixture() }

    // port
    val boardPersistencePortFixture: BoardPersistencePortFixture by lazy { BoardPersistencePortFixture() }
    val articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture by lazy { ArticleCategoryPersistencePortFixture() }

    // policy
    val boardNamePolicyFixture: BoardNamePolicyFixture by lazy { BoardNamePolicyFixture() }
    val boardDescriptionPolicyFixture: BoardDescriptionPolicyFixture by lazy { BoardDescriptionPolicyFixture() }
    val boardSlugPolicyFixture: BoardSlugPolicyFixture by lazy { BoardSlugPolicyFixture() }

    val articleCategoryNamePolicyFixture: ArticleCategoryNamePolicyFixture by lazy { ArticleCategoryNamePolicyFixture() }
    val articleCategorySlugPolicyFixture: ArticleCategorySlugPolicyFixture by lazy { ArticleCategorySlugPolicyFixture() }


    // mapper
    val boardCreateCommandMapper: BoardCreateCommandMapper by lazy {
        BoardCreateCommandMapper(
            boardNamePolicy = boardNamePolicyFixture,
            boardDescriptionPolicy = boardDescriptionPolicyFixture,
            boardSlugPolicy = boardSlugPolicyFixture,
            authUserLoader = authUserLoaderFixture,
            timeManager = timeManagerFixture
        )
    }

    val articleCategoryCreateCommandMapper : ArticleCategoryCreateCommandMapper by lazy {
        ArticleCategoryCreateCommandMapper(
            articleCategoryNamePolicy = articleCategoryNamePolicyFixture,
            articleCategorySlugPolicy = articleCategorySlugPolicyFixture,
            authUserLoader = authUserLoaderFixture,
            timeManager = timeManagerFixture
        )
    }

    // processor
    val boardCreateProcessor: BoardCreateProcessor by lazy {
        BoardCreateProcessor(
            boardPersistencePort = boardPersistencePortFixture,
        )
    }

    val articleCategoryCreateProcessor: ArticleCategoryCreateProcessor by lazy {
        ArticleCategoryCreateProcessor(
            boardPersistencePort = boardPersistencePortFixture,
            articleCategoryPersistencePort = articleCategoryPersistencePortFixture,
        )
    }

    // usecase
    val boardCreateUseCase: BoardCreateUseCase by lazy {
        BoardCreateUseCaseImpl(
            commandMapper = boardCreateCommandMapper,
            processor = boardCreateProcessor,
        )
    }

    val articleCategoryCreateUseCase: ArticleCategoryCreateUseCase by lazy {
        ArticleCategoryCreateUseCaseImpl(
            commandMapper = articleCategoryCreateCommandMapper,
            processor = articleCategoryCreateProcessor
        )
    }
}
