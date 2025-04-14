dependencies {
    implementation(project(":board-system-common:core"))
    testFixturesImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-common:token"))
    testFixturesImplementation(testFixtures(project(":board-system-common:token")))

    implementation(project(":board-system-member:member-domain"))
    testFixturesImplementation(testFixtures(project(":board-system-member:member-domain")))

    implementation(project(":board-system-common:logger"))
}
