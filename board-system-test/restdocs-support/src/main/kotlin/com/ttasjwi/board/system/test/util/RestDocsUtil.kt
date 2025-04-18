package com.ttasjwi.board.system.test.util

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.ResultActions

fun ResultActions.andDocument(
    identifier: String,
    vararg snippets: Snippet
): ResultActions {
    return andDo(document(identifier, *snippets))
}
