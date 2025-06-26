package com.ttasjwi.board.system.app.docs

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RestDocsForwardController {

    @PermitAll
    @GetMapping("/docs")
    fun docs(): String {
        return "forward:/docs/index.html"
    }
}
