package com.ttasjwi.board.system.app.config

import com.ttasjwi.board.system.common.auth.infra.token.config.JwtAccessTokenGenerateAdapterConfig
import com.ttasjwi.board.system.common.auth.infra.token.config.JwtAccessTokenParseAdapterConfig
import com.ttasjwi.board.system.common.auth.infra.token.config.JwtRefreshTokenGenerateAdapterConfig
import com.ttasjwi.board.system.common.auth.infra.token.config.JwtRefreshTokenParseAdapterConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(
    JwtAccessTokenGenerateAdapterConfig::class,
    JwtAccessTokenParseAdapterConfig::class,
    JwtRefreshTokenGenerateAdapterConfig::class,
    JwtRefreshTokenParseAdapterConfig::class,
)
@Configuration
class AppTokenConfig
