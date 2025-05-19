package com.ttasjwi.board.system.articlelike.domain.test.support

import com.ttasjwi.board.system.articlelike.domain.*
import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleDislikeCancelCommandMapper
import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleDislikeCreateCommandMapper
import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleLikeCancelCommandMapper
import com.ttasjwi.board.system.articlelike.domain.mapper.ArticleLikeCreateCommandMapper
import com.ttasjwi.board.system.articlelike.domain.port.fixture.*
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleDislikeCancelProcessor
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleDislikeCreateProcessor
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleLikeCancelProcessor
import com.ttasjwi.board.system.articlelike.domain.processor.ArticleLikeCreateProcessor
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

    val articleDislikeCountPersistencePortFixture: ArticleDislikeCountPersistencePortFixture by lazy {
        ArticleDislikeCountPersistencePortFixture()
    }

    val articleLikePersistencePortFixture: ArticleLikePersistencePortFixture by lazy {
        ArticleLikePersistencePortFixture()
    }

    val articleDislikePersistencePortFixture: ArticleDislikePersistencePortFixture by lazy {
        ArticleDislikePersistencePortFixture()
    }

    val articlePersistencePortFixture: ArticlePersistencePortFixture by lazy {
        ArticlePersistencePortFixture()
    }

    // mapper
    val articleLikeCreateCommandMapper: ArticleLikeCreateCommandMapper by lazy {
        ArticleLikeCreateCommandMapper(
            authUserLoader = authUserLoaderFixture,
            timeManager = timeManagerFixture
        )
    }

    val articleLikeCancelCommandMapper: ArticleLikeCancelCommandMapper by lazy {
        ArticleLikeCancelCommandMapper(
            authUserLoader = authUserLoaderFixture,
            timeManager = timeManagerFixture,
        )
    }

    val articleDislikeCreateCommandMapper: ArticleDislikeCreateCommandMapper by lazy {
        ArticleDislikeCreateCommandMapper(
            authUserLoader = authUserLoaderFixture,
            timeManager = timeManagerFixture
        )
    }

    val articleDislikeCancelCommandMapper: ArticleDislikeCancelCommandMapper by lazy {
        ArticleDislikeCancelCommandMapper(
            authUserLoader = authUserLoaderFixture,
            timeManager = timeManagerFixture,
        )
    }

    // processor
    val articleLikeCreateProcessor: ArticleLikeCreateProcessor by lazy {
        ArticleLikeCreateProcessor(
            articlePersistencePort = articlePersistencePortFixture,
            articleCategoryPersistencePort = articleCategoryPersistencePortFixture,
            articleLikePersistencePort = articleLikePersistencePortFixture,
            articleLikeCountPersistencePort = articleLikeCountPersistencePortFixture,
        )
    }

    val articleLikeCancelProcessor: ArticleLikeCancelProcessor by lazy {
        ArticleLikeCancelProcessor(
            articlePersistencePort = articlePersistencePortFixture,
            articleLikePersistencePort = articleLikePersistencePortFixture,
            articleLikeCountPersistencePort = articleLikeCountPersistencePortFixture
        )
    }

    val articleDislikeCreateProcessor: ArticleDislikeCreateProcessor by lazy {
        ArticleDislikeCreateProcessor(
            articlePersistencePort = articlePersistencePortFixture,
            articleCategoryPersistencePort = articleCategoryPersistencePortFixture,
            articleDislikePersistencePort = articleDislikePersistencePortFixture,
            articleDislikeCountPersistencePort = articleDislikeCountPersistencePortFixture,
        )
    }

    val articleDislikeCancelProcessor: ArticleDislikeCancelProcessor by lazy {
        ArticleDislikeCancelProcessor(
            articlePersistencePort = articlePersistencePortFixture,
            articleDislikePersistencePort = articleDislikePersistencePortFixture,
            articleDislikeCountPersistencePort = articleDislikeCountPersistencePortFixture
        )
    }

    // useCase
    val articleLikeCreateUseCase: ArticleLikeCreateUseCase by lazy {
        ArticleLikeCreateUseCaseImpl(
            commandMapper = articleLikeCreateCommandMapper,
            processor = articleLikeCreateProcessor,
        )
    }

    val articleLikeCancelUseCase: ArticleLikeCancelUseCase by lazy {
        ArticleLikeCancelUseCaseImpl(
            commandMapper = articleLikeCancelCommandMapper,
            processor = articleLikeCancelProcessor,
        )
    }

    val articleDislikeCreateUseCase: ArticleDislikeCreateUseCase by lazy {
        ArticleDislikeCreateUseCaseImpl(
            commandMapper = articleDislikeCreateCommandMapper,
            processor = articleDislikeCreateProcessor,
        )
    }

    val articleDislikeCancelUseCase: ArticleDislikeCancelUseCase by lazy {
        ArticleDislikeCancelUseCaseImpl(
            commandMapper = articleDislikeCancelCommandMapper,
            processor = articleDislikeCancelProcessor,
        )
    }
}
