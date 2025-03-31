package com.ttasjwi.board.system.spring.security.support

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.util.Assert
import org.springframework.util.CollectionUtils
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import java.security.MessageDigest
import java.security.cert.X509Certificate
import java.util.*
import java.util.function.Supplier


/**
 * 스프링 시큐리티의 NimbusJwtDecoder는 토큰 검증 과정에서 시간 검증을 함께 수행합니다.
 * 하지만 이 방식을 사용할 경우 테스트 통제가 힘들어집니다.
 * 그래서 시간 검증기를 제외하고 검증기 설정을 하기로 했습니다. (시간검증은 우리 코드에서 담당하도록 함)
 * 그런데 X509CertificateThumbprintValidator 역시 NimbusJwtDecoder 의 기본 검증기이지만, 생성자를 제공하지 않습니다.
 * 그래서 해당 코드를 그대로 복사, 붙여넣기 해서 검증기를 만들었습니다.
 */
internal class X509CertificateThumbprintValidator(x509CertificateSupplier: Supplier<X509Certificate?>) :
    OAuth2TokenValidator<Jwt> {
    private val logger: Log = LogFactory.getLog(javaClass)
    private val x509CertificateSupplier: Supplier<X509Certificate?>

    init {
        Assert.notNull(x509CertificateSupplier, "x509CertificateSupplier cannot be null")
        this.x509CertificateSupplier = x509CertificateSupplier
    }

    override fun validate(jwt: Jwt): OAuth2TokenValidatorResult {
        val confirmationMethodClaim = jwt.getClaim<Map<String?, Any?>>("cnf")
        var x509CertificateThumbprintClaim: String? = null
        if (!CollectionUtils.isEmpty(confirmationMethodClaim) && confirmationMethodClaim.containsKey("x5t#S256")) {
            x509CertificateThumbprintClaim = confirmationMethodClaim["x5t#S256"] as String?
        }
        if (x509CertificateThumbprintClaim == null) {
            return OAuth2TokenValidatorResult.success()
        }

        val x509Certificate = x509CertificateSupplier.get()
        if (x509Certificate == null) {
            val error = OAuth2Error(
                OAuth2ErrorCodes.INVALID_TOKEN,
                "Unable to obtain X509Certificate from current request.", null
            )
            if (logger.isDebugEnabled) {
                logger.debug(error.toString())
            }
            return OAuth2TokenValidatorResult.failure(error)
        }

        val x509CertificateThumbprint: String
        try {
            x509CertificateThumbprint = computeSHA256Thumbprint(x509Certificate)
        } catch (ex: Exception) {
            val error = OAuth2Error(
                OAuth2ErrorCodes.INVALID_TOKEN,
                "Failed to compute SHA-256 Thumbprint for X509Certificate.", null
            )
            if (logger.isDebugEnabled) {
                logger.debug(error.toString())
            }
            return OAuth2TokenValidatorResult.failure(error)
        }

        if (x509CertificateThumbprint != x509CertificateThumbprintClaim) {
            val error = OAuth2Error(
                OAuth2ErrorCodes.INVALID_TOKEN,
                "Invalid SHA-256 Thumbprint for X509Certificate.", null
            )
            if (logger.isDebugEnabled) {
                logger.debug(error.toString())
            }
            return OAuth2TokenValidatorResult.failure(error)
        }

        return OAuth2TokenValidatorResult.success()
    }

    private class DefaultX509CertificateSupplier : Supplier<X509Certificate?> {
        override fun get(): X509Certificate? {
            val requestAttributes = RequestContextHolder.getRequestAttributes() ?: return null

            val clientCertificateChain = requestAttributes
                .getAttribute(
                    "jakarta.servlet.request.X509Certificate",
                    RequestAttributes.SCOPE_REQUEST
                ) as Array<X509Certificate>

            return if ((clientCertificateChain != null && clientCertificateChain.isNotEmpty())) clientCertificateChain[0]
            else null
        }
    }

    companion object {
        val DEFAULT_X509_CERTIFICATE_SUPPLIER: Supplier<X509Certificate?> = DefaultX509CertificateSupplier()

        fun computeSHA256Thumbprint(x509Certificate: X509Certificate): String {
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(x509Certificate.encoded)
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
        }
    }
}
