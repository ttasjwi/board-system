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

    implementation(project(":board-system-member:member-domain"))
    testImplementation(testFixtures(project(":board-system-member:member-domain")))

    implementation(project(":board-system-member:member-application-input-port"))
    implementation(project(":board-system-member:member-application-output-port"))
    testImplementation(testFixtures(project(":board-system-member:member-application-output-port")))

    implementation(project(":board-system-common:id-generator"))
    implementation(project(":board-system-common:logger"))
}
