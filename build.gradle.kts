plugins {
    base
    `java-library`
    jacoco
}

allprojects {
    group = "io.github.zero88"

    ext {
        set("depVersions", mapOf("slf4j" to "1.7.30", "logback" to "1.2.3", "jackson" to "2.11.3",
                                 "lombok" to "1.18.16", "junit5" to "5.7.0", "jsonassert" to "1.5.0",
                                 "jooq" to "3.14.0", "rsql" to "2.1.0", "h2" to "1.4.200",
                                 "sonarqube" to "3.0"))
    }

    repositories {
        mavenLocal()
        maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
        maven { url = uri("https://maven.pkg.github.com/zero88/java-utils") }
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "eclipse")
    apply(plugin = "idea")
    apply(plugin = "jacoco")
    val depVersions: Map<String, String> by ext

    dependencies {
        compileOnlyApi("com.fasterxml.jackson.core:jackson-annotations:${depVersions["jackson"]}")
        compileOnly("org.projectlombok:lombok:${depVersions["lombok"]}")
        annotationProcessor("org.projectlombok:lombok:${depVersions["lombok"]}")

        testImplementation("org.skyscreamer:jsonassert:${depVersions["jsonassert"]}")
        testImplementation("org.junit.jupiter:junit-jupiter-api:${depVersions["junit5"]}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${depVersions["junit5"]}")
        testCompileOnly("org.projectlombok:lombok:${depVersions["lombok"]}")
        testAnnotationProcessor("org.projectlombok:lombok:${depVersions["lombok"]}")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

dependencies {
    subprojects.forEach {
        archives(it)
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}