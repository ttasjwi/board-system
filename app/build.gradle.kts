plugins {
    id(Plugins.KOTLIN_JPA.id) version Plugins.KOTLIN_JPA.version
}

dependencies {
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)

    // for database
    implementation(Dependencies.SPRING_BOOT_DATA_JPA.fullName)
    runtimeOnly(Dependencies.MYSQL_DRIVER.fullName)
    implementation(Dependencies.P6SPY_DATASOURCE_DECORATOR.fullName)

    // for email validator
    implementation(Dependencies.COMMONS_VALIDATOR.fullName)

    // for logging
    implementation(Dependencies.KOTLIN_LOGGING.fullName)
}
