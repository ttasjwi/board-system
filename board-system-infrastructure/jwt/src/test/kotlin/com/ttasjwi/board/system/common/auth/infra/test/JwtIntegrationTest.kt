package com.ttasjwi.board.system.common.auth.infra.test

import com.ttasjwi.board.system.common.auth.AccessTokenGeneratePort
import com.ttasjwi.board.system.common.auth.AccessTokenParsePort
import com.ttasjwi.board.system.common.auth.RefreshTokenGeneratePort
import com.ttasjwi.board.system.common.auth.RefreshTokenParsePort
import com.ttasjwi.board.system.common.auth.infra.token.config.JwtAccessTokenGenerateAdapterConfig
import com.ttasjwi.board.system.common.auth.infra.token.config.JwtAccessTokenParseAdapterConfig
import com.ttasjwi.board.system.common.auth.infra.token.config.JwtRefreshTokenGenerateAdapterConfig
import com.ttasjwi.board.system.common.auth.infra.token.config.JwtRefreshTokenParseAdapterConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(
    JwtAccessTokenGenerateAdapterConfig::class,
    JwtAccessTokenParseAdapterConfig::class,
    JwtRefreshTokenGenerateAdapterConfig::class,
    JwtRefreshTokenParseAdapterConfig::class,
)
abstract class JwtIntegrationTest {

    @Autowired
    protected lateinit var accessTokenGeneratePort: AccessTokenGeneratePort

    @Autowired
    protected lateinit var accessTokenParsePort: AccessTokenParsePort

    @Autowired
    protected lateinit var refreshTokenGeneratePort: RefreshTokenGeneratePort

    @Autowired
    protected lateinit var refreshTokenParsePort: RefreshTokenParsePort
}
