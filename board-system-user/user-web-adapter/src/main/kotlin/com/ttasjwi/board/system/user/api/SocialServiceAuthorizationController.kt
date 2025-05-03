package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.user.domain.SocialServiceAuthorizationUseCase
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SocialServiceAuthorizationController(
    private val socialServiceAuthorizationUseCase: SocialServiceAuthorizationUseCase
) {

    @PostMapping("/api/v1/auth/social-service-authorization/{socialServiceId}")
    fun authorize(@PathVariable("socialServiceId") socialServiceId: String): ResponseEntity<Void> {
        val url = socialServiceAuthorizationUseCase.generateAuthorizationRequestUri(socialServiceId)
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION, url)
            .build()
    }
}
