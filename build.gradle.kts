plugins {
    eclipse
    idea
    id(ZeroLibs.Plugins.oss) version ZeroLibs.Version.plugin
    id(ZeroLibs.Plugins.root) version ZeroLibs.Version.plugin apply false
    id(PluginLibs.jooq) version PluginLibs.Version.jooq apply false
    id(PluginLibs.nexusPublish) version PluginLibs.Version.nexusPublish
}

apply(plugin = ZeroLibs.Plugins.root)

allprojects {
    group = "io.github.zero88"

    repositories {
        mavenLocal()
        maven { url = uri("https://maven-central-asia.storage-download.googleapis.com/maven2/") }
        jcenter()
        maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
        mavenCentral()
    }
}

subprojects {
    apply(plugin = ZeroLibs.Plugins.oss)
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    tasks {
        javadoc {
            options {
                this as StandardJavadocDocletOptions
                if (JavaVersion.current().isJava8Compatible) {
                    addBooleanOption("Xdoclint:none", true)
                }
            }
        }
    }

    dependencies {
        compileOnly(JacksonLibs.annotations)
        compileOnly(UtilLibs.lombok)
        annotationProcessor(UtilLibs.lombok)

        testImplementation(TestLibs.jsonassert)
        testImplementation(TestLibs.junit5Api)
        testRuntimeOnly(TestLibs.junit5Engine)
        testCompileOnly(UtilLibs.lombok)
        testAnnotationProcessor(UtilLibs.lombok)
    }

    oss {
        zero88.set(true)
        publishingInfo {
            enabled.set(true)
            homepage.set("https://github.com/zero88/rsql")
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://github.com/zero88/rsql/blob/master/LICENSE")
            }
            scm {
                connection.set("scm:git:git://git@github.com:zero88/rsql.git")
                developerConnection.set("scm:git:ssh://git@github.com:zero88/rsql.git")
                url.set("https://github.com/zero88/rsql")
            }
        }
    }
}

nexusPublishing {
    packageGroup.set("io.github.zero88")
    repositories {
        sonatype {
            username.set(project.property("nexus.username") as String?)
            password.set(project.property("nexus.password") as String?)
        }
    }
}

tasks.register("generateJooq") {
    group = "jooq"
    dependsOn(subprojects.map { it.tasks.withType<nu.studer.gradle.jooq.JooqGenerate>() })
}