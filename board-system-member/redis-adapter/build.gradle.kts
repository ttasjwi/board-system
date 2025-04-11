dependencies {
    implementation(Dependencies.SPRING_BOOT_DATA_REDIS.fullName)
    implementation(project(":board-system-common:core"))
    implementation(project(":board-system-common:data-serializer"))
    implementation(project(":board-system-member:domain"))
    implementation(project(":board-system-member:application-output-port"))

    testImplementation(testFixtures(project(":board-system-common:core")))
    testImplementation(testFixtures(project(":board-system-member:domain")))
}
