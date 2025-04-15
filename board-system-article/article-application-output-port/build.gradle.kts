dependencies {
    implementation(project(":board-system-common:core"))
    testFixturesImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-article:article-domain"))
    testFixturesImplementation(testFixtures(project(":board-system-article:article-domain")))

    implementation(project(":board-system-common:logger"))
}
