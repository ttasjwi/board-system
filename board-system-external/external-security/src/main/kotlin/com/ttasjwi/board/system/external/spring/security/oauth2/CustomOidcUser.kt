package com.ttasjwi.board.system.external.spring.security.oauth2

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser

internal abstract class CustomOidcUser
protected constructor(
    socialServiceName: String,
    authorities: MutableCollection<out GrantedAuthority>,
    attributes: MutableMap<String, Any>,
) : OidcUser, SocialServiceUserPrincipal {

    private val _socialServiceName = socialServiceName
    private val _authorities = authorities
    private val _attributes = attributes

    override fun socialServiceName(): String {
        return _socialServiceName
    }

    companion object {


        private val oidcUserFactory: Map<String, (MutableCollection<out GrantedAuthority>, MutableMap<String, Any>) -> CustomOidcUser> =
            mapOf(
                "google" to ::GoogleOidcUser,
                "kakao" to ::KakaoOidcUser,
            )

        fun from(socialServiceName: String, oidcUser: OidcUser): CustomOidcUser {
            return oidcUserFactory[socialServiceName]?.invoke(oidcUser.authorities, oidcUser.attributes)
                ?: throw IllegalStateException("지원되지 않는 oidc 서비스 제공자: $socialServiceName")
        }
    }

    override fun getName(): String {
        return socialServiceUserId()
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

internal class GoogleOidcUser
internal constructor(
    authorities: MutableCollection<out GrantedAuthority>,
    attributes: MutableMap<String, Any>,
) : CustomOidcUser(
    socialServiceName = "google",
    authorities = authorities,
    attributes = attributes,
) {

    override fun socialServiceUserId(): String {
        return attributes["sub"] as String
    }

    override fun email(): String {
        return attributes["email"] as String
    }
}

internal class KakaoOidcUser
internal constructor(
    authorities: MutableCollection<out GrantedAuthority>,
    attributes: MutableMap<String, Any>,
) : CustomOidcUser(
    socialServiceName = "kakao",
    authorities = authorities,
    attributes = attributes,
) {

    override fun socialServiceUserId(): String {
        return attributes["sub"] as String
    }

    override fun email(): String {
        return attributes["email"] as String
    }
}
