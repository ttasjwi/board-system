package com.ttasjwi.board.system.common.infra.databasesupport.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.ttasjwi.board"]
)
@EntityScan(basePackages = ["com.ttasjwi.board"])
class JpaConfig
