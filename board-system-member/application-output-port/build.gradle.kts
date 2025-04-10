dependencies {
    implementation(project(":board-system-common:core"))
    testFixturesImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-member:domain"))
    testFixturesImplementation(testFixtures(project(":board-system-member:domain")))

    implementation(project(":board-system-common:logger"))
    implementation("org.springframework:spring-tx")
}
