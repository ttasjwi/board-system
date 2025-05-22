allOpen {
    annotation(
        "com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor"
    )
}

dependencies {
    implementation("org.springframework:spring-tx")
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-article-view:article-view-domain"))
    testImplementation(testFixtures(project(":board-system-article-view:article-view-domain")))

    implementation(project(":board-system-article-view:article-view-application-input-port"))
    implementation(project(":board-system-article-view:article-view-application-output-port"))
    testImplementation(testFixtures(project(":board-system-article-view:article-view-application-output-port")))

    implementation(project(":board-system-common:logger"))
}
