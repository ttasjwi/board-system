package com.ttasjwi.board.system.articleread.domain.test.support

import com.ttasjwi.board.system.articleread.domain.ArticleDetailReadUseCase
import com.ttasjwi.board.system.articleread.domain.ArticleDetailReadUseCaseImpl
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadUseCase
import com.ttasjwi.board.system.articleread.domain.ArticleSummaryPageReadUseCaseImpl
import com.ttasjwi.board.system.articleread.domain.mapper.ArticleDetailReadQueryMapper
import com.ttasjwi.board.system.articleread.domain.mapper.ArticleSummaryPageReadQueryMapper
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleDetailStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleSummaryStorageFixture
import com.ttasjwi.board.system.articleread.domain.port.fixture.ArticleViewCountStorageFixture
import com.ttasjwi.board.system.articleread.domain.processor.ArticleDetailReadProcessor
import com.ttasjwi.board.system.articleread.domain.processor.ArticleSummaryPageReadProcessor
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.auth.fixture.AuthUserLoaderFixture

internal class TestContainer private constructor(){

    companion object {
        fun create(): TestContainer {
            return TestContainer()
        }
    }


    val authUserLoaderFixture: AuthUserLoaderFixture by lazy {
        AuthUserLoaderFixture()
    }

    // port
    val articleDetailStorageFixture: ArticleDetailStorageFixture by lazy {
        ArticleDetailStorageFixture()
    }

    val articleSummaryStorageFixture: ArticleSummaryStorageFixture by lazy {
        ArticleSummaryStorageFixture()
    }

    val articleViewCountStorageFixture: ArticleViewCountStorageFixture by lazy {
        ArticleViewCountStorageFixture()
    }

    // mapper
    val articleDetailReadQueryMapper: ArticleDetailReadQueryMapper by lazy {
        ArticleDetailReadQueryMapper(
            authUserLoader = authUserLoaderFixture,
        )
    }

    val articleSummaryPageReadQueryMapper: ArticleSummaryPageReadQueryMapper by lazy {
        ArticleSummaryPageReadQueryMapper()
    }

    // processor
    val articleDetailReadProcessor: ArticleDetailReadProcessor by lazy {
        ArticleDetailReadProcessor(
            articleDetailStorage = articleDetailStorageFixture,
            articleViewCountStorage = articleViewCountStorageFixture,
        )
    }

    val articleSummaryPageReadProcessor: ArticleSummaryPageReadProcessor by lazy {
        ArticleSummaryPageReadProcessor(
            articleSummaryStorage = articleSummaryStorageFixture,
            articleViewCountStorage = articleViewCountStorageFixture,
        )
    }

    // useCase
    val articleDetailReadUseCase: ArticleDetailReadUseCase by lazy {
        ArticleDetailReadUseCaseImpl(
            queryMapper = articleDetailReadQueryMapper,
            processor = articleDetailReadProcessor
        )
    }

    val articleSummaryPageReadUseCase: ArticleSummaryPageReadUseCase by lazy {
        ArticleSummaryPageReadUseCaseImpl(
            queryMapper = articleSummaryPageReadQueryMapper,
            processor = articleSummaryPageReadProcessor
        )
    }

}
