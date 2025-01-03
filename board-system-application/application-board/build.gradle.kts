dependencies {
    implementation(project(":board-system-domain:domain-board"))
    implementation(project(":board-system-domain:domain-core"))
    testFixturesImplementation(testFixtures(project(":board-system-domain:domain-board")))
    testFixturesImplementation(testFixtures(project(":board-system-domain:domain-core")))
}
