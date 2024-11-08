dependencies {
    implementation(Dependencies.SPRING_BOOT_STARTER.fullName)
    implementation(project(":board-system-application:application-member"))
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-domain:domain-core"))

    testImplementation(testFixtures(project(":board-system-application:application-member")))
    testImplementation(testFixtures(project(":board-system-domain:domain-member")))
}
