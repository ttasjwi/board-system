import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// 루트 프로젝트
plugins {
    id(Plugins.KOTLIN_JVM.id) version Plugins.KOTLIN_JVM.version
    id(Plugins.KOTLIN_SPRING.id) version Plugins.KOTLIN_SPRING.version apply false
    id(Plugins.SPRING_BOOT.id) version Plugins.SPRING_BOOT.version apply false
    id(Plugins.SPRING_DEPENDENCY_MANAGEMENT.id) version Plugins.SPRING_DEPENDENCY_MANAGEMENT.version
    id(Plugins.JAVA_TEST_FIXTURES.id)
}

java {
    sourceCompatibility = JavaVersion.valueOf("VERSION_${ProjectProperties.JAVA_VERSION}")
}

// 루트 프로젝트 + 서브 프로젝트 전체
allprojects {
    group = ProjectProperties.GROUP_NAME
    version = ProjectProperties.VERSION

    repositories {
        mavenCentral()
    }
}

// 서브프로젝트에 적용
subprojects {
    apply { plugin(Plugins.KOTLIN_JVM.id) }
    apply { plugin(Plugins.KOTLIN_SPRING.id) }
    apply { plugin(Plugins.SPRING_BOOT.id) }
    apply { plugin(Plugins.SPRING_DEPENDENCY_MANAGEMENT.id) }
    apply { plugin(Plugins.JAVA_TEST_FIXTURES.id) }

    dependencies {

        val sharedModuleNames = listOf("board-system-core", "board-system-logging")

        if(project.name !in sharedModuleNames) {
            implementation(project(":board-system-core"))
            implementation(project(":board-system-logging"))
            testFixturesImplementation(testFixtures(project(":board-system-core")))
        }

        implementation(Dependencies.KOTLIN_REFLECT.fullName)
        testImplementation(Dependencies.SPRING_BOOT_TEST.fullName)
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = ProjectProperties.JAVA_VERSION
        }
    }

    tasks.test {
        useJUnitPlatform()
    }
}
