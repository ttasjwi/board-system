allOpen {
    annotation(
        "com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor"
    )
}

dependencies {
    implementation("org.springframework:spring-tx")
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))

    implementation(project(":board-system-common:token"))
    testImplementation(testFixtures(project(":board-system-common:token")))

    implementation(project(":board-system-user:user-domain"))
    testImplementation(testFixtures(project(":board-system-user:user-domain")))

    implementation(project(":board-system-user:user-application-input-port"))
    implementation(project(":board-system-user:user-application-output-port"))
    testImplementation(testFixtures(project(":board-system-user:user-application-output-port")))

    implementation(project(":board-system-common:id-generator"))
    implementation(project(":board-system-common:logger"))
}
