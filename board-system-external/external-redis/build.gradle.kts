dependencies {
    implementation(project(":board-system-domain:domain-core"))
    implementation(project(":board-system-domain:domain-member"))
    implementation(project(":board-system-domain:domain-auth"))

    testFixturesImplementation(testFixtures(project(":board-system-domain:domain-core")))
    testFixturesImplementation(testFixtures(project(":board-system-domain:domain-member")))
    testFixturesImplementation(testFixtures(project(":board-system-domain:domain-auth")))
}
