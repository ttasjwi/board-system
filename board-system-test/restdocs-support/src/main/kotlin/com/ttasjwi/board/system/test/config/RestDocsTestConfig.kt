package com.ttasjwi.board.system.test.config

import com.ttasjwi.board.system.common.infra.websupport.auth.config.CoreSecurityConfig
import com.ttasjwi.board.system.common.infra.websupport.exception.config.ExceptionHandlingConfig
import com.ttasjwi.board.system.common.infra.websupport.locale.config.LocaleConfig
import com.ttasjwi.board.system.common.infra.websupport.message.config.MessageConfig
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@TestConfiguration
@Import(
    ExceptionHandlingConfig::class,
    LocaleConfig::class,
    MessageConfig::class,
    CoreSecurityConfig::class,
    TestTimeConfig::class,
    TestTokenConfig::class,
    TestAopConfig::class
)
class CoreRestDocsTestConfig
