package com.ttasjwi.board.system.articleview.domain.test.support

import com.ttasjwi.board.system.articleview.domain.ArticleViewCountIncreaseUseCase
import com.ttasjwi.board.system.articleview.domain.ArticleViewCountIncreaseUseCaseImpl
import com.ttasjwi.board.system.articleview.domain.mapper.ArticleViewCountIncreaseCommandMapper
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticleViewCountLockPersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticleViewCountPersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.processor.ArticleViewCountIncreaseProcessor
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
    val articlePersistencePortFixture: ArticlePersistencePortFixture by lazy {
        ArticlePersistencePortFixture()
    }

    val articleViewCountPersistencePortFixture: ArticleViewCountPersistencePortFixture by lazy {
        ArticleViewCountPersistencePortFixture()
    }

    val articleViewCountLockPersistencePortFixture: ArticleViewCountLockPersistencePortFixture by lazy {
        ArticleViewCountLockPersistencePortFixture()
    }

    // mapper
    val articleViewCountIncreaseCommandMapper: ArticleViewCountIncreaseCommandMapper by lazy {
        ArticleViewCountIncreaseCommandMapper(
            authUserLoader = authUserLoaderFixture
        )
    }

    // processor
    val articleViewCountIncreaseProcessor : ArticleViewCountIncreaseProcessor by lazy {
        ArticleViewCountIncreaseProcessor(
            articlePersistencePort = articlePersistencePortFixture,
            articleViewCountPersistencePort = articleViewCountPersistencePortFixture,
            articleViewCountLockPersistencePort = articleViewCountLockPersistencePortFixture,
        )
    }

    // useCase
    val articleViewCountIncreaseUseCase: ArticleViewCountIncreaseUseCase by lazy {
        ArticleViewCountIncreaseUseCaseImpl(
            commandMapper = articleViewCountIncreaseCommandMapper,
            processor = articleViewCountIncreaseProcessor
        )
    }
}
