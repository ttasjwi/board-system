dependencies {
    implementation(project(":board-system-domain:domain-core"))
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-application:application-core"))

    testImplementation(testFixtures(project(":board-system-domain:domain-core")))
    testImplementation(testFixtures(project(":board-system-domain:domain-member")))
}
