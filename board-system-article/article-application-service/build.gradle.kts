allOpen {
    annotation(
        "com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor"
    )
}

dependencies {
    implementation("org.springframework:spring-tx")
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-article:article-domain"))
    testImplementation(testFixtures(project(":board-system-article:article-domain")))

    implementation(project(":board-system-article:article-application-input-port"))
    implementation(project(":board-system-article:article-application-output-port"))
    testImplementation(testFixtures(project(":board-system-article:article-application-output-port")))

    implementation(project(":board-system-common:id-generator"))
    implementation(project(":board-system-common:logger"))
}
