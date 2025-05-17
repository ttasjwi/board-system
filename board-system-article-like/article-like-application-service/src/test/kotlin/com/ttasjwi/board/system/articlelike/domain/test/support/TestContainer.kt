package com.ttasjwi.board.system.articlelike.domain.test.support

import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateUseCase
import com.ttasjwi.board.system.articlelike.domain.ArticleLikeCreateUseCaseImpl
import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleLikeCreateCommandMapper
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleLikeCountPersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticleLikePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleLikeCreateProcessor
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.auth.fixture.AuthUserLoaderFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture

internal class TestContainer private constructor() {

    companion object {

        fun create(): TestContainer {
            return TestContainer()
        }
    }

    val timeManagerFixture: TimeManagerFixture by lazy {
        TimeManagerFixture()
    }

    val authUserLoaderFixture: AuthUserLoaderFixture by lazy {
        AuthUserLoaderFixture()
    }

    // port
    val articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture by lazy {
        ArticleCategoryPersistencePortFixture()
    }

    val articleLikeCountPersistencePortFixture: ArticleLikeCountPersistencePortFixture by lazy {
        ArticleLikeCountPersistencePortFixture()
    }

    val articleLikePersistencePortFixture: ArticleLikePersistencePortFixture by lazy {
        ArticleLikePersistencePortFixture()
    }

    val articlePersistecnePortFixture: ArticlePersistencePortFixture by lazy {
        ArticlePersistencePortFixture()
    }

    // mapper
    val articleLikeCreateCommandMapper: ArticleLikeCreateCommandMapper by lazy {
        ArticleLikeCreateCommandMapper(
            authUserLoader = authUserLoaderFixture,
            timeManager = timeManagerFixture
        )
    }

    // processor
    val articleLikeCreateProcessor: ArticleLikeCreateProcessor by lazy {
        ArticleLikeCreateProcessor(
            articlePersistencePort = articlePersistecnePortFixture,
            articleCategoryPersistencePort = articleCategoryPersistencePortFixture,
            articleLikePersistencePort = articleLikePersistencePortFixture,
            articleLikeCountPersistencePort = articleLikeCountPersistencePortFixture,
        )
    }

    // useCase
    val articleLikeCreateUseCase: ArticleLikeCreateUseCase by lazy {
        ArticleLikeCreateUseCaseImpl(
            commandMapper = articleLikeCreateCommandMapper,
            processor = articleLikeCreateProcessor,
        )
    }
}
