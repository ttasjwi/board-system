package com.ttasjwi.board.system.deploy.api

class HealthCheckResponse(
    val serverName: String,
    val serverAddress: String,
    val serverPort: String,
    val serverEnv: String,
)
