dependencies {
    implementation(project(":board-system-application:application-core"))
    implementation(project(":board-system-domain:domain-board"))
    implementation(project(":board-system-domain:domain-core"))

    testImplementation(testFixtures(project(":board-system-application:application-core")))
    testFixturesImplementation(testFixtures(project(":board-system-domain:domain-board")))
    testFixturesImplementation(testFixtures(project(":board-system-domain:domain-core")))
}
