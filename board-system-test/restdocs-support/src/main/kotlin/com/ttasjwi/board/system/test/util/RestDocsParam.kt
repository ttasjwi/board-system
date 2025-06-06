package com.ttasjwi.board.system.test.util

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.QueryParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation

class RestDocsParam(
    val descriptor: ParameterDescriptor
) {

    private var constraint: String
        get() = descriptor.attributes["constraint"] as String
        set(value) {
            descriptor.attributes["constraint"] = value
        }

    infix fun isOptional(value: Boolean): RestDocsParam {
        if (value) descriptor.optional()
        return this
    }

    infix fun constraint(value: String): RestDocsParam {
        this.constraint = value
        return this
    }
}

infix fun String.paramMeans(
    description: String
): RestDocsParam {
    return createField(this, description)
}

private fun createField(
    value: String,
    description: String,
    optional: Boolean = false
): RestDocsParam {
    val descriptor = RequestDocumentation
        .parameterWithName(value)
        .description(description)

    if (optional) descriptor.optional()

    return RestDocsParam(descriptor)
}

fun pathParameters(vararg params: RestDocsParam): PathParametersSnippet {
    return RequestDocumentation.pathParameters(params.map { it.descriptor })
}

fun queryParameters(vararg params: RestDocsParam): QueryParametersSnippet {
    return RequestDocumentation.queryParameters(params.map { it.descriptor })
}
