package com.ttasjwi.board.system.articlelike.test.base

import com.ttasjwi.board.system.test.base.RestDocsTest
import com.ttasjwi.board.system.articlelike.test.config.ArticleLikeRestDocsTestConfig
import org.springframework.context.annotation.Import

@Import(ArticleLikeRestDocsTestConfig::class)
abstract class ArticleLikeRestDocsTest : RestDocsTest()
