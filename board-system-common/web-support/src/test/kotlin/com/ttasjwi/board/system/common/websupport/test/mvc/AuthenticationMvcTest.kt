package com.ttasjwi.board.system.common.websupport.test.mvc

import com.nimbusds.jose.util.StandardCharset
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.common.websupport.test.WebSupportIntegrationTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@DisplayName("MVC 통합 인증/인가 테스트")
class AuthenticationMvcTest : WebSupportIntegrationTest() {

    @Test
    @DisplayName("Authorization Header 에 'Bearer '로 시작하지 않는 값을 보내면 그 어떤 엔드포인트더라도 접근할 수 없다.")
    fun inValidAuthorizationHeaderFormatTest() {
        mockMvc
            .perform(
                get("/api/v1/test/web-support/auth/permit-all")
                    .header(HttpHeaders.AUTHORIZATION, "Basic someToken")
                    .characterEncoding(StandardCharset.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpectAll(
                status().isUnauthorized,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.errors[0].code").value("Error.InvalidAuthorizationHeaderFormat"),
                jsonPath("$.errors[0].message").value("Error.InvalidAuthorizationHeaderFormat.message"),
                jsonPath("$.errors[0].description").value("Error.InvalidAuthorizationHeaderFormat.description"),
                jsonPath("$.errors[0].source").value("authorizationHeader"),
            )
    }

    @Test
    @DisplayName("액세스 토큰이 만료됐다면, 그 어떤 엔드포인트더라도 접근할 수 없다.")
    fun expiredAccessTokenTest() {
        val tokenValue = generateAccessTokenValue(
            memberId = 1L,
            role = Role.USER,
            issuedAt = appDateTimeFixture(minute = 0),
            expiresAt = appDateTimeFixture(minute = 30)
        )

        timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 45))

        mockMvc
            .perform(
                get("/api/v1/test/web-support/auth/permit-all")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer $tokenValue")
                    .characterEncoding(StandardCharset.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpectAll(
                status().isUnauthorized,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.errors[0].code").value("Error.AccessTokenExpired"),
                jsonPath("$.errors[0].message").value("Error.AccessTokenExpired.message"),
                jsonPath("$.errors[0].description").value("Error.AccessTokenExpired.description"),
                jsonPath("$.errors[0].source").value("accessToken"),
            )
    }

    @Nested
    @DisplayName("PermitAll : 인증이 필요하지 않은 엔드포인트에 대한 테스트")
    inner class PermitAllTest {

        @Test
        @DisplayName("액세스 토큰이 없어도 인증이 필요하지 않은 엔드포인트에는 접근 가능하다.")
        fun test1() {
            mockMvc
                .perform(
                    get("/api/v1/test/web-support/auth/permit-all")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk,
                    content().contentType("text/plain;charset=UTF-8"),
                    content().string("permit-all")
                )
        }

        @Test
        @DisplayName("인증된 사용자는 기본적으로 인증이 필요하지 않은 엔드포인트에도 접근 가능하다.")
        fun test2() {
            val tokenValue = generateAccessTokenValue(
                memberId = 1L,
                role = Role.USER,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )

            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 22))

            mockMvc
                .perform(
                    get("/api/v1/test/web-support/auth/permit-all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $tokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk,
                    content().contentType("text/plain;charset=UTF-8"),
                    content().string("permit-all")
                )
        }
    }

    @Nested
    @DisplayName("RequireAuthenticated: 인증이 필요한 엔드포인트")
    inner class RequireAuthenticated {

        @Test
        @DisplayName("액세스토큰 미지참시, 접근 불가능")
        fun noTokenTest() {
            mockMvc
                .perform(
                    get("/api/v1/test/web-support/auth/authenticated")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isUnauthorized,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.errors[0].code").value("Error.Unauthenticated"),
                    jsonPath("$.errors[0].message").value("Error.Unauthenticated.message"),
                    jsonPath("$.errors[0].description").value("Error.Unauthenticated.description"),
                    jsonPath("$.errors[0].source").value("credentials"),
                )
        }

        @Test
        @DisplayName("액세스토큰이 유효하고 일반 사용자이면, 접근 가능")
        fun roleUserTest() {
            // given
            val authMember = authMemberFixture(memberId = 5544, role = Role.USER)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )

            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            val path = "/api/v1/test/web-support/auth/authenticated"
            mockMvc
                .perform(
                    get(path)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.path").value(path),
                    jsonPath("$.memberId").value(authMember.memberId),
                    jsonPath("$.role").value(authMember.role.name)
                )
        }

        @Test
        @DisplayName("액세스토큰이 유효하고 어드민 사용자이면, 접근 가능")
        fun roleAdminTest() {
            // given
            val authMember = authMemberFixture(memberId = 3488, role = Role.ADMIN)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )

            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            val path = "/api/v1/test/web-support/auth/authenticated"
            mockMvc
                .perform(
                    get(path)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.path").value(path),
                    jsonPath("$.memberId").value(authMember.memberId),
                    jsonPath("$.role").value(authMember.role.name)
                )
        }

        @Test
        @DisplayName("액세스토큰이 유효하고 루트 사용자이면, 접근 가능")
        fun roleRootTest() {
            // given
            val authMember = authMemberFixture(memberId = 5544, role = Role.ROOT)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )

            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            val path = "/api/v1/test/web-support/auth/authenticated"
            mockMvc
                .perform(
                    get("/api/v1/test/web-support/auth/authenticated")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.path").value(path),
                    jsonPath("$.memberId").value(authMember.memberId),
                    jsonPath("$.role").value(authMember.role.name)
                )
        }
    }

    @Nested
    @DisplayName("RequireAdminRole: 어드민 이상의 사용자만 접근 가능")
    inner class RequireAdminRole {

        @Test
        @DisplayName("액세스토큰 미지참시, 접근 불가능")
        fun noTokenTest() {
            mockMvc
                .perform(
                    get("/api/v1/test/web-support/auth/admin-role")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isUnauthorized,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.errors[0].code").value("Error.Unauthenticated"),
                    jsonPath("$.errors[0].message").value("Error.Unauthenticated.message"),
                    jsonPath("$.errors[0].description").value("Error.Unauthenticated.description"),
                    jsonPath("$.errors[0].source").value("credentials"),
                )
        }

        @Test
        @DisplayName("액세스토큰이 유효하고 일반사용자이면, 접근 불가능")
        fun roleUserTest() {
            // given
            val authMember = authMemberFixture(memberId = 5544, role = Role.USER)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )

            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            mockMvc
                .perform(
                    get("/api/v1/test/web-support/auth/admin-role")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isForbidden,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.errors[0].code").value("Error.AccessDenied"),
                    jsonPath("$.errors[0].message").value("Error.AccessDenied.message"),
                    jsonPath("$.errors[0].description").value("Error.AccessDenied.description"),
                    jsonPath("$.errors[0].source").value("credentials"),
                )
        }


        @Test
        @DisplayName("액세스토큰이 유효하고 어드민 사용자이면, 접근 가능")
        fun roleAdminTest() {
            // given
            val authMember = authMemberFixture(memberId = 3488, role = Role.ADMIN)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )

            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            val path = "/api/v1/test/web-support/auth/admin-role"
            mockMvc
                .perform(
                    get(path)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.path").value(path),
                    jsonPath("$.memberId").value(authMember.memberId),
                    jsonPath("$.role").value(authMember.role.name)
                )
        }

        @Test
        @DisplayName("액세스토큰이 유효하고 루트 사용자이면, 접근 가능")
        fun roleRootTest() {
            // given
            val authMember = authMemberFixture(memberId = 5544, role = Role.ROOT)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )

            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            val path = "/api/v1/test/web-support/auth/admin-role"
            mockMvc
                .perform(
                    get(path)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.path").value(path),
                    jsonPath("$.memberId").value(authMember.memberId),
                    jsonPath("$.role").value(authMember.role.name)
                )
        }
    }

    @Nested
    @DisplayName("RequireRootRole: 루트 사용자만 접근 가능")
    inner class RequireRootRole {

        @Test
        @DisplayName("액세스토큰 미지참시, 접근 불가능")
        fun noTokenTest() {
            mockMvc
                .perform(
                    get("/api/v1/test/web-support/auth/root-role")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isUnauthorized,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.errors[0].code").value("Error.Unauthenticated"),
                    jsonPath("$.errors[0].message").value("Error.Unauthenticated.message"),
                    jsonPath("$.errors[0].description").value("Error.Unauthenticated.description"),
                    jsonPath("$.errors[0].source").value("credentials"),
                )
        }

        @Test
        @DisplayName("액세스토큰이 유효하고 일반 사용자이면, 접근 불가능")
        fun roleUserTest() {
            // given
            val authMember = authMemberFixture(memberId = 5544, role = Role.USER)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )
            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            mockMvc
                .perform(
                    get("/api/v1/test/web-support/auth/admin-role")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isForbidden,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.errors[0].code").value("Error.AccessDenied"),
                    jsonPath("$.errors[0].message").value("Error.AccessDenied.message"),
                    jsonPath("$.errors[0].description").value("Error.AccessDenied.description"),
                    jsonPath("$.errors[0].source").value("credentials"),
                )
        }

        @Test
        @DisplayName("액세스토큰이 유효하고 어드민 사용자이면, 접근 불가능")
        fun roleAdminTest() {
            // given
            val authMember = authMemberFixture(memberId = 3488, role = Role.ADMIN)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )

            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            val path = "/api/v1/test/web-support/auth/root-role"
            mockMvc
                .perform(
                    get(path)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isForbidden,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.errors[0].code").value("Error.AccessDenied"),
                    jsonPath("$.errors[0].message").value("Error.AccessDenied.message"),
                    jsonPath("$.errors[0].description").value("Error.AccessDenied.description"),
                    jsonPath("$.errors[0].source").value("credentials"),
                )
        }

        @Test
        @DisplayName("액세스토큰이 유효하고 루트 사용자이면, 접근 가능")
        fun roleRootTest() {
            // given
            val authMember = authMemberFixture(memberId = 5544, role = Role.ROOT)
            val accessTokenValue = generateAccessTokenValue(
                memberId = authMember.memberId,
                role = authMember.role,
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 30)
            )
            timeManagerFixture.changeCurrentTime(appDateTimeFixture(minute = 5))

            val path = "/api/v1/test/web-support/auth/root-role"
            mockMvc
                .perform(
                    get(path)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $accessTokenValue")
                        .characterEncoding(StandardCharset.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk,
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.path").value(path),
                    jsonPath("$.memberId").value(authMember.memberId),
                    jsonPath("$.role").value(authMember.role.name)
                )
        }
    }
}
