dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation(project(":board-system-common:core"))
    testImplementation(testFixtures(project(":board-system-common:core")))
}
