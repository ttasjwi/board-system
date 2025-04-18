package com.ttasjwi.board.system.article.test.base

import com.ttasjwi.board.system.article.test.config.ArticleRestDocsTestConfig
import com.ttasjwi.board.system.test.base.RestDocsTest
import org.springframework.context.annotation.Import

@Import(ArticleRestDocsTestConfig::class)
abstract class ArticleRestDocsTest : RestDocsTest()
