package com.ttasjwi.board.system.test.util

import org.springframework.http.HttpHeaders
import org.springframework.restdocs.operation.OperationRequest
import org.springframework.restdocs.operation.OperationRequestFactory
import org.springframework.restdocs.operation.OperationResponse
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor

val conditionalAuthorizationMasker = object : OperationPreprocessor {
    override fun preprocess(request: OperationRequest): OperationRequest {
        // 헤더 복사 후 Authorization만 마스킹
        val newHeaders = HttpHeaders()
        newHeaders.putAll(request.headers)
        if (request.headers.containsKey("Authorization")) {
            newHeaders["Authorization"] = listOf("Bearer AccessToken")
        }

        // 새 OperationRequest 생성
        return OperationRequestFactory().create(
            request.uri,
            request.method,
            request.content,
            newHeaders,
            request.parts,
            request.cookies
        )
    }

    override fun preprocess(response: OperationResponse): OperationResponse {
        return response // 응답은 그대로 반환
    }
}
