package com.ttasjwi.board.system.article.domain.test.support

import com.ttasjwi.board.system.article.domain.ArticleCreateUseCase
import com.ttasjwi.board.system.article.domain.ArticleCreateUseCaseImpl
import com.ttasjwi.board.system.article.domain.mapper.ArticleCreateCommandMapper
import com.ttasjwi.board.system.article.domain.policy.fixture.ArticleContentPolicyFixture
import com.ttasjwi.board.system.article.domain.policy.fixture.ArticleTitlePolicyFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.processor.ArticleCreateProcessor
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
    val articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture by lazy { ArticleCategoryPersistencePortFixture() }
    val articlePersistencePortFixture: ArticlePersistencePortFixture by lazy { ArticlePersistencePortFixture() }

    // domain policy
    val articleTitlePolicyFixture: ArticleTitlePolicyFixture by lazy { ArticleTitlePolicyFixture() }
    val articleContentPolicyFixture: ArticleContentPolicyFixture by lazy { ArticleContentPolicyFixture() }

    // command mapper
    val articleCreateCommandMapper: ArticleCreateCommandMapper by lazy {
        ArticleCreateCommandMapper(
            articleTitlePolicy = articleTitlePolicyFixture,
            articleContentPolicy = articleContentPolicyFixture,
            authUserLoader = authUserLoaderFixture,
            timeManager = timeManagerFixture,
        )
    }

    // processor
    val articleCreateProcessor: ArticleCreateProcessor by lazy {
        ArticleCreateProcessor(
            articlePersistencePort = articlePersistencePortFixture,
            articleCategoryPersistencePort = articleCategoryPersistencePortFixture,
        )
    }

    // usecase
    val articleCreateUseCase: ArticleCreateUseCase by lazy {
        ArticleCreateUseCaseImpl(
            commandMapper = articleCreateCommandMapper,
            processor = articleCreateProcessor,
        )
    }
}
