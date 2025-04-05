package com.ttasjwi.board.system.global.springsecurity.oauth2

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.function.Consumer

class CustomOAuth2AuthorizationRequestResolver
private constructor(
    private val defaultOAuth2AuthorizationRequestResolver: DefaultOAuth2AuthorizationRequestResolver,
    private val requestMatcher: RequestMatcher
) : OAuth2AuthorizationRequestResolver {

    companion object {
        private const val REGISTRATION_ID_URI_VARIABLE_NAME = "registrationId"
        private val DEFAULT_PKCE_APPLIER: Consumer<OAuth2AuthorizationRequest.Builder> =
            OAuth2AuthorizationRequestCustomizers.withPkce()

        fun of(clientRegistrationRepository: ClientRegistrationRepository,
               authorizationRequestBaseUri: String): CustomOAuth2AuthorizationRequestResolver {
            return CustomOAuth2AuthorizationRequestResolver(
                defaultOAuth2AuthorizationRequestResolver = DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri),
                requestMatcher = AntPathRequestMatcher("${authorizationRequestBaseUri}/{$REGISTRATION_ID_URI_VARIABLE_NAME}")
            )
        }
    }

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return resolve(request, resolveClientRegistrationId(request))
    }

    override fun resolve(request: HttpServletRequest, clientRegistrationId: String?): OAuth2AuthorizationRequest? {
        if (clientRegistrationId == null) {
            return null
        }

        // 예외가 발생하거나(provider id 안 맞아서)
        // null 이 아닌 request 가 만들어짐
        val defaultResolverResolvedRequest = defaultOAuth2AuthorizationRequestResolver.resolve(request, clientRegistrationId)!!
        val builder = OAuth2AuthorizationRequest.from(defaultResolverResolvedRequest)

        // pkce 적용
        DEFAULT_PKCE_APPLIER.accept(builder)

        return builder.build()
    }

    private fun resolveClientRegistrationId(request: HttpServletRequest): String? {
        if (requestMatcher.matches(request)) {
            return requestMatcher.matcher(request).variables[REGISTRATION_ID_URI_VARIABLE_NAME]
        }
        return null
    }
}
