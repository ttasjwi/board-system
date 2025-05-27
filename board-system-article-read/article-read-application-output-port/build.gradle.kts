dependencies {
    implementation(project(":board-system-common:core"))
    testFixturesImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-article-read:article-read-domain"))
    testFixturesImplementation(testFixtures(project(":board-system-article-read:article-read-domain")))
}
