
dependencies {
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-article-read:article-read-domain"))
    testImplementation(testFixtures(project(":board-system-article-read:article-read-domain")))

    implementation(project(":board-system-article-read:article-read-application-input-port"))
    implementation(project(":board-system-article-read:article-read-application-output-port"))
    testImplementation(testFixtures(project(":board-system-article-read:article-read-application-output-port")))

    implementation(project(":board-system-common:logger"))
}
