plugins {
    id(Plugins.KOTLIN_JPA.id) version Plugins.KOTLIN_JPA.version
}

allOpen {
    annotation("com.ttasjwi.board.system.global.annotation.DomainService")
    annotation("com.ttasjwi.board.system.global.annotation.ApplicationService")
    annotation("com.ttasjwi.board.system.global.annotation.ApplicationProcessor")
    annotation("com.ttasjwi.board.system.global.annotation.ApplicationCommandMapper")
    annotation("com.ttasjwi.board.system.global.annotation.ApplicationQueryMapper")
}

dependencies {
    // web
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)

    // security
    implementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    implementation(Dependencies.SPRING_BOOT_OAUTH2_CLIENT.fullName)

    // for database
    implementation(Dependencies.SPRING_BOOT_DATA_JPA.fullName)
    runtimeOnly(Dependencies.MYSQL_DRIVER.fullName)
    implementation(Dependencies.P6SPY_DATASOURCE_DECORATOR.fullName)

    // for redis
    implementation(Dependencies.SPRING_BOOT_DATA_REDIS.fullName)

    // for email-send
    implementation(Dependencies.SPRING_BOOT_MAIL.fullName)

    // for email-format-validation
    implementation(Dependencies.COMMONS_VALIDATOR.fullName)

    // for logging
    implementation(Dependencies.KOTLIN_LOGGING.fullName)

    // for data-json-serialization
    implementation(Dependencies.KOTLIN_JACKSON.fullName)
    implementation(Dependencies.JACKSON_DATETIME.fullName)

    // for yaml message
    implementation(Dependencies.YAML_RESOURCE_BUNDLE.fullName)
}
