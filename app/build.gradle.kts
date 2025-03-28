plugins {
    id(Plugins.KOTLIN_JPA.id) version Plugins.KOTLIN_JPA.version
}

dependencies {
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)

    // for database
    implementation(Dependencies.SPRING_BOOT_DATA_JPA.fullName)
    runtimeOnly(Dependencies.MYSQL_DRIVER.fullName)
    implementation(Dependencies.P6SPY_DATASOURCE_DECORATOR.fullName)

    // for redis
    implementation(Dependencies.SPRING_BOOT_DATA_REDIS.fullName)

    // for email-format-validation
    implementation(Dependencies.COMMONS_VALIDATOR.fullName)

    // for logging
    implementation(Dependencies.KOTLIN_LOGGING.fullName)

    // for data-json-serialization
    implementation(Dependencies.KOTLIN_JACKSON.fullName)
    implementation(Dependencies.JACKSON_DATETIME.fullName)
}
