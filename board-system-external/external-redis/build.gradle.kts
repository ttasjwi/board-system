dependencies {
    implementation(Dependencies.SPRING_BOOT_DATA_REDIS.fullName)
    implementation(Dependencies.KOTLIN_JACKSON.fullName)
    implementation(Dependencies.JACKSON_DATETIME.fullName)
    implementation(project(":board-system-domain:domain-core"))
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-domain:domain-auth"))

    testImplementation(testFixtures(project(":board-system-domain:domain-core")))
    testImplementation(testFixtures(project(":board-system-domain:domain-member")))
    testImplementation(testFixtures(project(":board-system-domain:domain-auth")))
}
