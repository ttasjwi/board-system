package com.ttasjwi.board.system.deploy.api.response

class HealthCheckResponse(
    val serverEnv: String,
    val serverAddress: String,
    val serverPort: String,
    val serverName: String
)
