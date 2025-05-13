package com.ttasjwi.board.system.articlecomment.test.base

import com.ttasjwi.board.system.articlecomment.test.config.ArticleCommentRestDocsTestConfig
import com.ttasjwi.board.system.test.base.RestDocsTest
import org.springframework.context.annotation.Import

@Import(ArticleCommentRestDocsTestConfig::class)
abstract class ArticleCommentRestDocsTest : RestDocsTest()
