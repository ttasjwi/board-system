plugins {
    id(Plugins.KOTLIN_JPA.id) version Plugins.KOTLIN_JPA.version
}

dependencies {
    implementation(Dependencies.SPRING_BOOT_DATA_JPA.fullName)
    runtimeOnly(Dependencies.MYSQL_DRIVER.fullName)
    implementation(Dependencies.P6SPY_DATASOURCE_DECORATOR.fullName)
    implementation(project(":board-system-domain:domain-core"))
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-application:application-core"))

    testImplementation(testFixtures(project(":board-system-domain:domain-core")))
    testImplementation(testFixtures(project(":board-system-domain:domain-member")))
}
