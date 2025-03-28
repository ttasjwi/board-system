package com.ttasjwi.board.system.config

import com.p6spy.engine.common.ConnectionInformation
import com.p6spy.engine.event.JdbcEventListener
import com.p6spy.engine.spy.P6SpyOptions
import org.springframework.stereotype.Component
import java.sql.SQLException

@Component
class P6SpyEventListener : JdbcEventListener() {

    override fun onAfterGetConnection(connectionInformation: ConnectionInformation?, e: SQLException?) {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6SpyFormattingStrategy::class.java.getName()
    }
}
