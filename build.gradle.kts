import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.KOTLIN_JVM.id) version Plugins.KOTLIN_JVM.version
    id(Plugins.KOTLIN_SPRING.id) version Plugins.KOTLIN_SPRING.version
    id(Plugins.SPRING_BOOT.id) version Plugins.SPRING_BOOT.version
    id(Plugins.SPRING_DEPENDENCY_MANAGEMENT.id) version Plugins.SPRING_DEPENDENCY_MANAGEMENT.version
}

group = ProjectProperties.GROUP_NAME
version = ProjectProperties.VERSION

java {
    sourceCompatibility = JavaVersion.valueOf("VERSION_${ProjectProperties.JAVA_VERSION}")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependencies.SPRING_BOOT_WEB.fullName)
    implementation(Dependencies.KOTLIN_REFLECT.fullName)
    implementation(Dependencies.KOTLIN_JACKSON.fullName)
    testImplementation(Dependencies.SPRING_BOOT_TEST.fullName)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = ProjectProperties.JAVA_VERSION
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
