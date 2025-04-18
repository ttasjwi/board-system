plugins {
    id(Plugins.KOTLIN_JPA.id) version Plugins.KOTLIN_JPA.version
}

dependencies {
    implementation(Dependencies.SPRING_BOOT_DATA_JPA.fullName)
    runtimeOnly(Dependencies.MYSQL_DRIVER.fullName)

    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-board:board-domain"))
    testImplementation(testFixtures(project(":board-system-board:board-domain")))

    implementation(project(":board-system-board:board-application-output-port"))
}
