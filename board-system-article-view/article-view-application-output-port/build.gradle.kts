dependencies {
    implementation(project(":board-system-common:core"))
    testFixturesImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-article-view:article-view-domain"))
    testFixturesImplementation(testFixtures(project(":board-system-article-view:article-view-domain")))
}
