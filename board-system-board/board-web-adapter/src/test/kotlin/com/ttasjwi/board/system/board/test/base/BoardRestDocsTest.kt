package com.ttasjwi.board.system.board.test.base

import com.ttasjwi.board.system.board.test.config.BoardRestDocsTestConfig
import com.ttasjwi.board.system.test.base.RestDocsTest
import org.springframework.context.annotation.Import

@Import(BoardRestDocsTestConfig::class)
abstract class BoardRestDocsTest : RestDocsTest()
