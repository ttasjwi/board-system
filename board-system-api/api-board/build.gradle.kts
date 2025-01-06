dependencies {
    implementation(project(":board-system-api:api-core"))
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation(Dependencies.KOTLIN_JACKSON.fullName)

    implementation(project(":board-system-application:application-board"))
    testImplementation(testFixtures(project(":board-system-application:application-board")))
}
