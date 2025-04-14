dependencies {
    implementation(project(":board-system-common:core"))
    testFixturesImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-board:board-domain"))
    testFixturesImplementation(testFixtures(project(":board-system-board:board-domain")))

    implementation(project(":board-system-common:logger"))
}
