dependencies {
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation(Dependencies.SPRING_BOOT_SECURITY.fullName)
    implementation(Dependencies.SPRING_BOOT_OAUTH2_CLIENT.fullName)
    implementation(Dependencies.YAML_RESOURCE_BUNDLE.fullName)
    implementation(project(":board-system-common:core"))
    implementation(project(":board-system-common:token"))
    implementation(project(":board-system-common:logger"))
    testImplementation(testFixtures(project(":board-system-common:core")))
    testImplementation(testFixtures(project(":board-system-common:token")))
}
