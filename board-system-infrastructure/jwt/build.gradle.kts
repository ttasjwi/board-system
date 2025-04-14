dependencies {
    implementation(project(":board-system-common:core"))
    implementation(project(":board-system-common:token"))
    implementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    implementation(Dependencies.SPRING_BOOT_OAUTH2_CLIENT.fullName)

    testImplementation(testFixtures(project(":board-system-common:core")))
    testImplementation(testFixtures(project(":board-system-common:token")))
}
