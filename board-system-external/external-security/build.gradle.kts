dependencies {
    implementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation(Dependencies.SPRING_BOOT_OAUTH2_CLIENT.fullName)
    implementation(project(":board-system-domain:domain-core"))
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-domain:domain-auth"))
    testImplementation(testFixtures(project(":board-system-domain:domain-core")))
    testImplementation(testFixtures(project(":board-system-domain:domain-member")))
    testImplementation(testFixtures(project(":board-system-domain:domain-auth")))
}
