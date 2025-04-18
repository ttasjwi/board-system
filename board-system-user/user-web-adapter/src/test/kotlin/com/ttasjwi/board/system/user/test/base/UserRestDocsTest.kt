package com.ttasjwi.board.system.user.test.base

import com.ttasjwi.board.system.test.base.RestDocsTest
import com.ttasjwi.board.system.user.test.config.UserRestDocsTestConfig
import org.springframework.context.annotation.Import

@Import(UserRestDocsTestConfig::class)
abstract class UserRestDocsTest : RestDocsTest()
