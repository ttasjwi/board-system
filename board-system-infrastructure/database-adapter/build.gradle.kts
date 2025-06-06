plugins {
    id(Plugins.KOTLIN_JPA.id) version Plugins.KOTLIN_JPA.version
}

dependencies {
    implementation(Dependencies.SPRING_BOOT_DATA_JPA.fullName)
    runtimeOnly(Dependencies.MYSQL_DRIVER.fullName)
    implementation(Dependencies.P6SPY_DATASOURCE_DECORATOR.fullName)

    implementation(Dependencies.QUERY_DSL_JPA.fullName)
    kapt(Dependencies.QUERY_DSL_APT.fullName)

    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    // user
    implementation(project(":board-system-user:user-domain"))
    testImplementation(testFixtures(project(":board-system-user:user-domain")))
    implementation(project(":board-system-user:user-application-output-port"))

    // board
    implementation(project(":board-system-board:board-domain"))
    testImplementation(testFixtures(project(":board-system-board:board-domain")))
    implementation(project(":board-system-board:board-application-output-port"))

    // article
    implementation(project(":board-system-article:article-domain"))
    testImplementation(testFixtures(project(":board-system-article:article-domain")))
    implementation(project(":board-system-article:article-application-output-port"))

    // article-comment
    implementation(project(":board-system-article-comment:article-comment-domain"))
    testImplementation(testFixtures(project(":board-system-article-comment:article-comment-domain")))
    implementation(project(":board-system-article-comment:article-comment-application-output-port"))

    // article-like
    implementation(project(":board-system-article-like:article-like-domain"))
    testImplementation(testFixtures(project(":board-system-article-like:article-like-domain")))
    implementation(project(":board-system-article-like:article-like-application-output-port"))

    // article-view
    implementation(project(":board-system-article-view:article-view-domain"))
    testImplementation(testFixtures(project(":board-system-article-view:article-view-domain")))
    implementation(project(":board-system-article-view:article-view-application-output-port"))

    // article-read
    implementation(project(":board-system-article-read:article-read-domain"))
    testImplementation(testFixtures(project(":board-system-article-read:article-read-domain")))
    implementation(project(":board-system-article-read:article-read-application-output-port"))
}
