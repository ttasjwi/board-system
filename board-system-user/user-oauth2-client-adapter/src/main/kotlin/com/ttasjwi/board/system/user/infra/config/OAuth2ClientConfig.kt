package com.ttasjwi.board.system.user.infra.config

import com.ttasjwi.board.system.user.domain.model.OAuth2ClientAuthenticationMethod
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.infra.oauth2.MemoryOAuth2ClientRegistrationPersistenceAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository

@Configuration
class OAuth2ClientConfig {

    @Bean
    fun memoryOAuth2ClientRegistrationPersistenceAdapter(
        clientRegistrationRepository: InMemoryClientRegistrationRepository
    ): MemoryOAuth2ClientRegistrationPersistenceAdapter {
        val registrations = mutableMapOf<String, OAuth2ClientRegistration>()
        for (clientRegistration in clientRegistrationRepository) {
            val registrationId = clientRegistration.registrationId
            val oauth2ClientRegistration = OAuth2ClientRegistration(
                registrationId = clientRegistration.registrationId,
                clientId = clientRegistration.clientId,
                clientSecret = clientRegistration.clientSecret,
                clientAuthenticationMethod = OAuth2ClientAuthenticationMethod.restore(clientRegistration.clientAuthenticationMethod.value),
                redirectUri = clientRegistration.redirectUri,
                scopes = clientRegistration.scopes,
                clientName = clientRegistration.clientName,
                providerDetails = OAuth2ClientRegistration.ProviderDetails(
                    authorizationUri = clientRegistration.providerDetails.authorizationUri,
                    tokenUri = clientRegistration.providerDetails.tokenUri,
                    userInfoEndpoint = OAuth2ClientRegistration.ProviderDetails.UserInfoEndpoint(
                        uri = clientRegistration.providerDetails.userInfoEndpoint.uri,
                        authenticationMethod = OAuth2ClientAuthenticationMethod.restore(clientRegistration.providerDetails.userInfoEndpoint.authenticationMethod.value),
                        usernameAttributeName = clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
                    ),
                    jwkSetUri = clientRegistration.providerDetails.jwkSetUri,
                    issuerUri = clientRegistration.providerDetails.issuerUri,
                    configurationMetadata = clientRegistration.providerDetails.configurationMetadata
                )
            )
            registrations[registrationId] = oauth2ClientRegistration
        }
        return MemoryOAuth2ClientRegistrationPersistenceAdapter(registrations)
    }
}
