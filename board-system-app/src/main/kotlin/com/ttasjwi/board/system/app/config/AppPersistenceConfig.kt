package com.ttasjwi.board.system.app.config

import com.ttasjwi.board.system.common.databasesupport.config.JpaConfig
import com.ttasjwi.board.system.common.databasesupport.config.P6SpyConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    JpaConfig::class,
    P6SpyConfig::class
)
class AppPersistenceConfig
