dependencies {
    implementation(Dependencies.SPRING_BOOT_MAIL.fullName)
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))
}
