package com.ttasjwi.board.system.articleread.test.base

import com.ttasjwi.board.system.articleread.test.config.ArticleReadRestDocsTestConfig
import com.ttasjwi.board.system.test.base.RestDocsTest
import org.springframework.context.annotation.Import

@Import(ArticleReadRestDocsTestConfig::class)
abstract class ArticleReadRestDocsTest : RestDocsTest()
