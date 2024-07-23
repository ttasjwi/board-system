import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.KOTLIN_JVM.id) version Plugins.KOTLIN_JVM.version
    id(Plugins.KOTLIN_SPRING.id) version Plugins.KOTLIN_SPRING.version
    id(Plugins.SPRING_BOOT.id) version Plugins.SPRING_BOOT.version
    id(Plugins.SPRING_DEPENDENCY_MANAGEMENT.id) version Plugins.SPRING_DEPENDENCY_MANAGEMENT.version
}

java {
    sourceCompatibility = JavaVersion.valueOf("VERSION_${ProjectProperties.JAVA_VERSION}")
}

allprojects {
    group = ProjectProperties.GROUP_NAME
    version = ProjectProperties.VERSION

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply{ plugin(Plugins.KOTLIN_JVM.id) }
    apply{ plugin(Plugins.KOTLIN_SPRING.id) }
    apply{ plugin(Plugins.SPRING_BOOT.id) }
    apply{ plugin(Plugins.SPRING_DEPENDENCY_MANAGEMENT.id) }

    dependencies {
        implementation(Dependencies.KOTLIN_REFLECT.fullName)
        testImplementation(Dependencies.SPRING_BOOT_TEST.fullName)
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }
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
