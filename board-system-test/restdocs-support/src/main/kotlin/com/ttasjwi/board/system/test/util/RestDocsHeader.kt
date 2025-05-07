package com.ttasjwi.board.system.test.util

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.RequestHeadersSnippet
import org.springframework.restdocs.headers.ResponseHeadersSnippet

class RestDocsHeader(
    val descriptor: HeaderDescriptor
) {

    private var example: String
        get() = descriptor.attributes["example"] as String
        set(value) {
            descriptor.attributes["example"] = value
        }

    infix fun example(value: String): RestDocsHeader {
        this.example = value
        return this
    }

    infix fun isOptional(value: Boolean): RestDocsHeader {
        if (value) descriptor.optional()
        return this
    }
}

infix fun String.headerMeans(
    description: String
): RestDocsHeader {
    return createField(this, description)
}

private fun createField(
    value: String,
    description: String,
    optional: Boolean = false
): RestDocsHeader {
    val descriptor = HeaderDocumentation
        .headerWithName(value)
        .description(description)

    if (optional) descriptor.optional()

    return RestDocsHeader(descriptor)
}

fun requestHeaders(vararg params: RestDocsHeader): RequestHeadersSnippet {
    return HeaderDocumentation.requestHeaders(params.map { it.descriptor })
}

fun responseHeaders(vararg params: RestDocsHeader): ResponseHeadersSnippet {
    return HeaderDocumentation.responseHeaders(params.map { it.descriptor })
}
