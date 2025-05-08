package com.ttasjwi.board.system.test.util

import org.springframework.restdocs.payload.*

class RestDocsField(
    val descriptor: FieldDescriptor
) {

    private var default: String
        get() = descriptor.attributes["default"] as String
        set(value) {
            descriptor.attributes["default"] = value
        }

    private var constraint: String
        get() = descriptor.attributes["constraint"] as String
        set(value) {
            descriptor.attributes["constraint"] = value
        }

    infix fun means(value: String): RestDocsField {
        descriptor.description(value)
        return this
    }

    infix fun constraint(value: String): RestDocsField {
        this.constraint = value
        return this
    }

    infix fun attributes(block: RestDocsField.() -> Unit): RestDocsField {
        block()
        return this
    }

    infix fun withDefaultValue(value: String): RestDocsField {
        this.default = value
        return this
    }

    infix fun isOptional(value: Boolean): RestDocsField {
        if (value) {
            descriptor.optional()
        }
        return this
    }

    infix fun isIgnored(value: Boolean): RestDocsField {
        if (value) descriptor.ignored()
        return this
    }
}

infix fun String.type(
    docsFieldType: DocsFieldType
): RestDocsField {
    return createField(this, docsFieldType.type)
}

infix fun String.subSectionType(
    typeName: String,
): RestDocsField {
    val descriptor = PayloadDocumentation.subsectionWithPath(this)
        .type(typeName)
    return RestDocsField(descriptor)
}

private fun createField(
    value: String,
    type: JsonFieldType,
    optional: Boolean = false
): RestDocsField {
    val descriptor = PayloadDocumentation.fieldWithPath(value)
        .type(type)
        .description("")

    if (optional) descriptor.optional()

    return RestDocsField(descriptor)
}

fun requestBody(vararg fields: RestDocsField): RequestFieldsSnippet {
    return PayloadDocumentation.requestFields(fields.map { it.descriptor })
}

fun responseBody(vararg fields: RestDocsField): ResponseFieldsSnippet {
    return PayloadDocumentation.responseFields(fields.map { it.descriptor })
}
