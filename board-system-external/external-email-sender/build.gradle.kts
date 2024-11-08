dependencies {
    implementation(Dependencies.SPRING_BOOT_MAIL.fullName)
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-domain:domain-core"))

    testImplementation(testFixtures(project(":board-system-domain:domain-member")))
    testImplementation(testFixtures(project(":board-system-domain:domain-core")))
}
