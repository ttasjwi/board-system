package com.ttasjwi.board.system.external.spring.security.oauth2

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser

internal abstract class CustomOidcUser
protected constructor(
    private val _providerName: String,
    private val _authorities: MutableCollection<out GrantedAuthority>,
    private val _attributes: MutableMap<String, Any>,
) : OidcUser, ProviderUser {

    override fun providerName(): String {
        return _providerName
    }

    companion object {

        private val oidcUserFactory: Map<String, (OidcUser, String) -> CustomOidcUser> = mapOf(
            "google" to ::GoogleOidcUser,
            "kakao" to ::KakaoOidcUser,
        )

        fun of(oidcUser: OidcUser, providerName: String): CustomOidcUser {
            return oidcUserFactory[providerName]?.invoke(oidcUser, providerName)
                ?: throw IllegalStateException("지원되지 않는 oidc provider: $providerName")
        }
    }

    override fun getName(): String {
        return userId()
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return _attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return _authorities
    }

    override fun getIdToken(): OidcIdToken {
        throw UnsupportedOperationException("This method should not be called")
    }

    override fun getClaims(): MutableMap<String, Any> {
        throw UnsupportedOperationException("This method should not be called")
    }

    override fun getUserInfo(): OidcUserInfo {
        throw UnsupportedOperationException("This method should not be called")
    }
}

internal class GoogleOidcUser(
    oidcUser: OidcUser,
    providerName: String,
) : CustomOidcUser(
    _providerName = providerName,
    _authorities = oidcUser.authorities,
    _attributes = oidcUser.attributes,
) {

    override fun userId(): String {
        return attributes["sub"] as String
    }

    override fun email(): String {
        return attributes["email"] as String
    }
}

internal class KakaoOidcUser(
    oidcUser: OidcUser,
    providerName: String,
) : CustomOidcUser(
    _providerName = providerName,
    _authorities = oidcUser.authorities,
    _attributes = oidcUser.attributes,
) {
    override fun userId(): String {
        return attributes["sub"] as String
    }

    override fun email(): String {
        return attributes["email"] as String
    }
}
