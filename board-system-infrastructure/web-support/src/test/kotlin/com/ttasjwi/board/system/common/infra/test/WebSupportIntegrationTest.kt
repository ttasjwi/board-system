package com.ttasjwi.board.system.common.infra.test

import com.ttasjwi.board.system.common.auth.AccessTokenGeneratePort
import com.ttasjwi.board.system.common.auth.AccessTokenParsePort
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.infra.test.config.TestFilterConfig
import com.ttasjwi.board.system.common.infra.test.config.TestSecurityConfig
import com.ttasjwi.board.system.common.infra.test.config.TestTimeConfig
import com.ttasjwi.board.system.common.infra.test.config.TestTokenConfig
import com.ttasjwi.board.system.common.infra.websupport.exception.config.ExceptionHandlingConfig
import com.ttasjwi.board.system.common.infra.websupport.locale.config.LocaleConfig
import com.ttasjwi.board.system.common.infra.websupport.message.config.MessageConfig
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureMockMvc
@SpringBootTest
@Import(
    TestTimeConfig::class,
    TestSecurityConfig::class,
    MessageConfig::class,
    LocaleConfig::class,
    ExceptionHandlingConfig::class,
    TestFilterConfig::class,
    TestTokenConfig::class,
)
abstract class WebSupportIntegrationTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var timeManagerFixture: TimeManagerFixture

    @Autowired
    protected lateinit var accessTokenGeneratePort: AccessTokenGeneratePort

    @Autowired
    protected lateinit var accessTokenParsePort: AccessTokenParsePort

    @BeforeEach
    fun setup() {
        timeManagerFixture.changeCurrentTime(appDateTimeFixture())
    }

    protected fun generateAccessTokenValue(
        memberId: Long,
        role: Role,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): String {
        return accessTokenGeneratePort.generate(
            authUser = authUserFixture(memberId, role),
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        ).tokenValue
    }
}
