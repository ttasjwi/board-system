package com.ttasjwi.board.system.test.util

import org.springframework.restdocs.payload.JsonFieldType

sealed class DocsFieldType(
	val type: JsonFieldType
)

object ARRAY : DocsFieldType(JsonFieldType.ARRAY)
object BOOLEAN : DocsFieldType(JsonFieldType.BOOLEAN)
object OBJECT : DocsFieldType(JsonFieldType.OBJECT)
object NUMBER : DocsFieldType(JsonFieldType.NUMBER)
object NULL : DocsFieldType(JsonFieldType.NULL)
object STRING : DocsFieldType(JsonFieldType.STRING)
object ANY : DocsFieldType(JsonFieldType.VARIES)
object DATE : DocsFieldType(JsonFieldType.STRING)
object DATETIME : DocsFieldType(JsonFieldType.STRING)
