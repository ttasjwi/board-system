package com.ttasjwi.board.system.articleview.test.base

import com.ttasjwi.board.system.articleview.test.config.ArticleViewRestDocsTestConfig
import com.ttasjwi.board.system.test.base.RestDocsTest
import org.springframework.context.annotation.Import

@Import(ArticleViewRestDocsTestConfig::class)
abstract class ArticleViewRestDocsTest : RestDocsTest()
