allOpen {
    annotation(
        "com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor"
    )
}

dependencies {
    implementation("org.springframework:spring-tx")
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-article-like:article-like-domain"))
    testImplementation(testFixtures(project(":board-system-article-like:article-like-domain")))

    implementation(project(":board-system-article-like:article-like-application-input-port"))
    implementation(project(":board-system-article-like:article-like-application-output-port"))
    testImplementation(testFixtures(project(":board-system-article-like:article-like-application-output-port")))

    implementation(project(":board-system-common:id-generator"))
    implementation(project(":board-system-common:logger"))
}
