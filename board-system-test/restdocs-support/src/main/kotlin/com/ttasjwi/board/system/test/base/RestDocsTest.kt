package com.ttasjwi.board.system.test.base

import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.AccessTokenPortFixture
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.dataserializer.DataSerializer
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.config.CoreRestDocsTestConfig
import com.ttasjwi.board.system.test.util.conditionalAuthorizationMasker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@Import(CoreRestDocsTestConfig::class)
@ExtendWith(RestDocumentationExtension::class)
abstract class RestDocsTest {

    protected lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var timeManagerFixture: TimeManagerFixture

    @Autowired
    private lateinit var accessTokenPortFixture: AccessTokenPortFixture

    @BeforeEach
    fun setup(context: WebApplicationContext, provider: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(provider)
                    .operationPreprocessors()
                    .withRequestDefaults(
                        Preprocessors.prettyPrint(),
                        Preprocessors.modifyUris().scheme("http").host("board-system.site").removePort(),
                        conditionalAuthorizationMasker,
                    )
                    .withResponseDefaults(Preprocessors.prettyPrint())
            )
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()
    }

    protected fun generateAccessToken(
        userId: Long = 1L,
        role: Role = Role.USER,
        issuedAt: AppDateTime = appDateTimeFixture(minute = 0),
        expiresAt: AppDateTime = issuedAt.plusMinutes(30)
    ): AccessToken {
        return accessTokenPortFixture.generate(authUserFixture(userId, role),issuedAt, expiresAt)
    }

    protected fun changeCurrentTime(currentTime: AppDateTime) {
        timeManagerFixture.changeCurrentTime(currentTime)
    }

    protected fun serializeToJson(obj: Any): String {
        return DataSerializer.serialize(obj)
    }

    protected fun <T> deserializeFromJson(json: String, clazz: Class<T>): T {
        return DataSerializer.deserialize(json, clazz)
    }
}
