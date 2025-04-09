package com.ttasjwi.board.system.common.websupport.test

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.common.token.AccessTokenGenerator
import com.ttasjwi.board.system.common.token.AccessTokenParser
import com.ttasjwi.board.system.common.websupport.exception.config.ExceptionHandlingConfig
import com.ttasjwi.board.system.common.websupport.locale.config.LocaleConfig
import com.ttasjwi.board.system.common.websupport.message.config.MessageConfig
import com.ttasjwi.board.system.common.websupport.test.config.TestFilterConfig
import com.ttasjwi.board.system.common.websupport.test.config.TestSecurityConfig
import com.ttasjwi.board.system.common.websupport.test.config.TestTimeConfig
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
    TestFilterConfig::class
)
abstract class WebSupportIntegrationTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var timeManagerFixture: TimeManagerFixture

    @Autowired
    protected lateinit var accessTokenGenerator: AccessTokenGenerator

    @Autowired
    protected lateinit var accessTokenParser: AccessTokenParser

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
        return accessTokenGenerator.generate(
            authMember = authMemberFixture(memberId, role),
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        ).tokenValue
    }
}
