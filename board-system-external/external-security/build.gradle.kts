dependencies {
    implementation(Dependencies.SPRING_BOOT_STARTER.fullName)
    implementation(Dependencies.SPRING_SECURITY_CRYPTO.fullName)
    implementation(project(":board-system-domain:domain-core"))
    implementation(project(":board-system-domain:domain-member"))
    testImplementation(testFixtures(project(":board-system-domain:domain-core")))
    testImplementation(testFixtures(project(":board-system-domain:domain-member")))
}
