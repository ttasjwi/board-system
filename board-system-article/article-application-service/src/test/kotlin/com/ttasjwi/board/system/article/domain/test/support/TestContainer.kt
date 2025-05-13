package com.ttasjwi.board.system.article.domain.test.support

import com.ttasjwi.board.system.article.domain.*
import com.ttasjwi.board.system.article.domain.mapper.ArticleCreateCommandMapper
import com.ttasjwi.board.system.article.domain.mapper.ArticleInfiniteScrollReadQueryMapper
import com.ttasjwi.board.system.article.domain.mapper.ArticlePageReadQueryMapper
import com.ttasjwi.board.system.article.domain.policy.fixture.ArticleContentPolicyFixture
import com.ttasjwi.board.system.article.domain.policy.fixture.ArticleTitlePolicyFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticleWriterNicknamePersistencePortFixture
import com.ttasjwi.board.system.article.domain.processor.ArticleCreateProcessor
import com.ttasjwi.board.system.article.domain.processor.ArticleInfiniteScrollReadProcessor
import com.ttasjwi.board.system.article.domain.processor.ArticlePageReadProcessor
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
    val articleWriterNicknamePersistencePortFixture by lazy { ArticleWriterNicknamePersistencePortFixture() }

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

    // query mapper
    val articlePageReadQueryMapper: ArticlePageReadQueryMapper by lazy {
        ArticlePageReadQueryMapper()
    }
    val articleInfiniteScrollReadQueryMapper: ArticleInfiniteScrollReadQueryMapper by lazy {
        ArticleInfiniteScrollReadQueryMapper()
    }

    // processor
    val articleCreateProcessor: ArticleCreateProcessor by lazy {
        ArticleCreateProcessor(
            articlePersistencePort = articlePersistencePortFixture,
            articleCategoryPersistencePort = articleCategoryPersistencePortFixture,
            articleWriterNicknamePersistencePort = articleWriterNicknamePersistencePortFixture,
        )
    }

    val articlePageReadProcessor: ArticlePageReadProcessor by lazy {
        ArticlePageReadProcessor(
            articlePersistencePort = articlePersistencePortFixture,
        )
    }

    val articleInfiniteScrollReadProcessor: ArticleInfiniteScrollReadProcessor by lazy {
        ArticleInfiniteScrollReadProcessor(
            articlePersistencePort = articlePersistencePortFixture,
        )
    }

    // usecase
    val articleCreateUseCase: ArticleCreateUseCase by lazy {
        ArticleCreateUseCaseImpl(
            commandMapper = articleCreateCommandMapper,
            processor = articleCreateProcessor,
        )
    }

    val articleReadUseCase: ArticleReadUseCase by lazy {
        ArticleReadUseCaseImpl(
            articlePersistencePort = articlePersistencePortFixture,
        )
    }

    val articlePageReadUseCase: ArticlePageReadUseCase by lazy {
        ArticlePageReadUseCaseImpl(
            queryMapper = articlePageReadQueryMapper,
            processor = articlePageReadProcessor,
        )
    }

    val articleInfiniteScrollReadUseCase: ArticleInfiniteScrollReadUseCase by lazy {
        ArticleInfiniteScrollReadUseCaseImpl(
            queryMapper = articleInfiniteScrollReadQueryMapper,
            processor = articleInfiniteScrollReadProcessor
        )
    }
}
