import org.gradle.internal.jvm.Jvm
import org.gradle.util.GradleVersion
import java.time.Instant
import java.util.jar.Attributes.Name

plugins {
    base
    `java-library`
    `maven-publish`
    jacoco
    signing
    id("org.sonarqube") version "3.0"
    id("io.codearte.nexus-staging") version "0.22.0"
}
val jacocoHtml: String? by project
val semanticVersion: String by project

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
    apply(plugin = "signing")
    apply(plugin = "maven-publish")
    val depVersions: Map<String, String> by ext
    project.version = "$version$semanticVersion"

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        withJavadocJar()
        withSourcesJar()
    }

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

    tasks {
        jar {
            manifest {
                attributes(
                    mapOf(Name.IMPLEMENTATION_TITLE.toString() to project.name,
                          Name.IMPLEMENTATION_VERSION.toString() to project.version,
                          "Created-By" to GradleVersion.current(),
                          "Build-Jdk" to Jvm.current(),
                          "Build-By" to project.property("buildBy"),
                          "Build-Hash" to project.property("buildHash"),
                          "Build-Date" to Instant.now())
                )
            }
        }
        javadoc {
            options {
                this as StandardJavadocDocletOptions
                tags = mutableListOf("apiNote:a:API Note:", "implSpec:a:Implementation Requirements:",
                                     "implNote:a:Implementation Note:")
            }
        }
        test {
            useJUnitPlatform()
        }
    }

    publishing {
        publications {
            repositories {
                create<MavenPublication>("maven") {
                    groupId = project.group as String?
                    artifactId = project.name
                    version = project.version as String?
                    from(components["java"])

                    versionMapping {
                        usage("java-api") {
                            fromResolutionOf("runtimeClasspath")
                        }
                        usage("java-runtime") {
                            fromResolutionResult()
                        }
                    }
                    pom {
                        name.set(project.name)
                        description.set("Universal RQL for SQL")
                        url.set("https://github.com/zero88/universal-rsql")
                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("https://github.com/zero88/universal-rsql/blob/master/LICENSE")
                            }
                        }
                        developers {
                            developer {
                                id.set("zero88")
                                email.set("sontt246@gmail.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:git://git@github.com:zero88/universal-rsql.git")
                            developerConnection.set("scm:git:ssh://git@github.com:zero88/universal-rsql.git")
                            url.set("https://github.com/zero88/universal-rsql")
                        }
                    }
                }
                maven {
                    val path = if (project.hasProperty("github")) {
                        "${project.property("github.nexus.url")}/${project.property("nexus.username")}/${project.name}"
                    } else {
                        val releasesRepoUrl = project.property("ossrh.release.url")
                        val snapshotsRepoUrl = project.property("ossrh.snapshot.url")
                        if (project.hasProperty("release")) releasesRepoUrl else snapshotsRepoUrl
                    }
                    url = path?.let { uri(it) }!!
                    credentials {
                        username = project.property("nexus.username") as String?
                        password = project.property("nexus.password") as String?
                    }
                }
            }
        }
    }

    signing {
        useGpgCmd()
        sign(publishing.publications["maven"])
    }
}

task<JacocoReport>("jacocoRootReport") {
    dependsOn(subprojects.map { it.tasks.withType<Test>() })
    dependsOn(subprojects.map { it.tasks.withType<JacocoReport>() })
    additionalSourceDirs.setFrom(subprojects.map { it.sourceSets.main.get().allSource.srcDirs })
    sourceDirectories.setFrom(subprojects.map { it.sourceSets.main.get().allSource.srcDirs })
    classDirectories.setFrom(subprojects.map { it.sourceSets.main.get().output })
    executionData.setFrom(project.fileTree(".") {
        include("**/build/jacoco/test.exec")
    })
    reports {
        csv.isEnabled = false
        xml.isEnabled = true
        xml.destination = file("${buildDir}/reports/jacoco/coverage.xml")
        html.isEnabled = (jacocoHtml ?: "true").toBoolean()
        html.destination = file("${buildDir}/reports/jacoco/html")
    }
}

project.tasks["sonarqube"].group = "analysis"
project.tasks["sonarqube"].dependsOn("build", "jacocoRootReport")
sonarqube {
    properties {
        property("jacocoHtml", "false")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco/coverage.xml")
    }
}

task<Sign>("sign") {
    dependsOn(subprojects.map { it.tasks.withType<Sign>() })
}

nexusStaging {
    packageGroup = project.group as String?
    username = project.property("nexus.username") as String?
    password = project.property("nexus.password") as String?
}