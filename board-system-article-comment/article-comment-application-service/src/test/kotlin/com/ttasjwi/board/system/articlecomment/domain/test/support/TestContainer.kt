package com.ttasjwi.board.system.articlecomment.domain.test.support

import com.ttasjwi.board.system.articlecomment.domain.*
import com.ttasjwi.board.system.articlecomment.domain.mapper.ArticleCommentCreateCommandMapper
import com.ttasjwi.board.system.articlecomment.domain.mapper.ArticleCommentInfiniteScrollReadQueryMapper
import com.ttasjwi.board.system.articlecomment.domain.mapper.ArticleCommentPageReadQueryMapper
import com.ttasjwi.board.system.articlecomment.domain.policy.fixture.ArticleCommentContentPolicyFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentCountPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentWriterNicknamePersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.processor.ArticleCommentCreateProcessor
import com.ttasjwi.board.system.articlecomment.domain.processor.ArticleCommentInfiniteScrollReadProcessor
import com.ttasjwi.board.system.articlecomment.domain.processor.ArticleCommentPageReadProcessor
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
    val articleCommentPersistencePortFixture: ArticleCommentPersistencePortFixture by lazy {
        ArticleCommentPersistencePortFixture()
    }

    val articleCommentCountPersistencePortFixture: ArticleCommentCountPersistencePortFixture by lazy {
        ArticleCommentCountPersistencePortFixture()
    }

    val articlePersistencePortFixture: ArticlePersistencePortFixture by lazy {
        ArticlePersistencePortFixture()
    }

    val articleCommentWriterNicknamePersistencePortFixture: ArticleCommentWriterNicknamePersistencePortFixture by lazy {
        ArticleCommentWriterNicknamePersistencePortFixture()
    }

    val articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture by lazy {
        ArticleCategoryPersistencePortFixture()
    }

    // domain policy
    val articleCommentContentPolicyFixture: ArticleCommentContentPolicyFixture by lazy {
        ArticleCommentContentPolicyFixture()
    }

    // mapper
    val articleCommentCreateCommandMapper: ArticleCommentCreateCommandMapper by lazy {
        ArticleCommentCreateCommandMapper(
            articleCommentContentPolicy = articleCommentContentPolicyFixture,
            timeManager = timeManagerFixture,
            authUserLoader = authUserLoaderFixture,
        )
    }

    val articleCommentPageReadQueryMapper: ArticleCommentPageReadQueryMapper by lazy {
        ArticleCommentPageReadQueryMapper()
    }

    val articleCommentInfiniteScrollReadQueryMapper: ArticleCommentInfiniteScrollReadQueryMapper by lazy {
        ArticleCommentInfiniteScrollReadQueryMapper()
    }

    // processor
    val articleCommentCreateProcessor: ArticleCommentCreateProcessor by lazy {
        ArticleCommentCreateProcessor(
            articleCommentPersistencePort = articleCommentPersistencePortFixture,
            articleCommentCountPersistencePort = articleCommentCountPersistencePortFixture,
            articlePersistencePort = articlePersistencePortFixture,
            articleCategoryPersistencePort = articleCategoryPersistencePortFixture,
            articleCommentWriterNicknamePersistencePort = articleCommentWriterNicknamePersistencePortFixture,
        )
    }

    val articleCommentPageReadProcessor: ArticleCommentPageReadProcessor by lazy {
        ArticleCommentPageReadProcessor(
            articleCommentPersistencePort = articleCommentPersistencePortFixture,
        )
    }

    val articleCommentInfiniteScrollReadProcessor: ArticleCommentInfiniteScrollReadProcessor by lazy {
        ArticleCommentInfiniteScrollReadProcessor(
            articleCommentPersistencePort = articleCommentPersistencePortFixture,
        )
    }

    // useCase
    val articleCommentCreateUseCase: ArticleCommentCreateUseCase by lazy {
        ArticleCommentCreateUseCaseImpl(
            commandMapper = articleCommentCreateCommandMapper,
            processor = articleCommentCreateProcessor,
        )
    }

    val articleCommentReadUseCase: ArticleCommentReadUseCase by lazy {
        ArticleCommentReadUseCaseImpl(
            articleCommentPersistencePort = articleCommentPersistencePortFixture
        )
    }

    val articleCommentCountReadUseCase: ArticleCommentCountReadUseCase by lazy {
        ArticleCommentCountReadUseCaseImpl(
            articlePersistencePort = articlePersistencePortFixture,
            articleCommentCountPersistencePort = articleCommentCountPersistencePortFixture,
        )
    }

    val articleCommentPageReadUseCase: ArticleCommentPageReadUseCase by lazy {
        ArticleCommentPageReadUseCaseImpl(
            queryMapper = articleCommentPageReadQueryMapper,
            processor = articleCommentPageReadProcessor,
        )
    }

    val articleCommentInfiniteScrollReadUseCase: ArticleCommentInfiniteScrollReadUseCase by lazy {
        ArticleCommentInfiniteScrollReadUseCaseImpl(
            queryMapper = articleCommentInfiniteScrollReadQueryMapper,
            processor = articleCommentInfiniteScrollReadProcessor,
        )
    }
}
