import org.jooq.meta.jaxb.Property

plugins {
    id("nu.studer.jooq") version "5.2"
}

val depVersions: Map<String, String> by ext

dependencies {
    api(project(":rql"))
    api("org.jooq:jooq:${depVersions["jooq"]}")

    testImplementation(project(":rql"))
    testImplementation("ch.qos.logback:logback-classic:${depVersions["logback"]}")
    testImplementation("com.h2database:h2:${depVersions["h2"]}")
    testImplementation("org.jooq:jooq-meta:${depVersions["jooq"]}")
    testImplementation("org.jooq:jooq-meta-extensions:${depVersions["jooq"]}")
    testImplementation("org.jooq:jooq-codegen:${depVersions["jooq"]}")
    jooqGenerator("org.jooq:jooq-meta-extensions:${depVersions["jooq"]}")
}

sourceSets.test {
    java.srcDirs("src/test/java", "generated/test/java")
}

jooq {
    version.set(depVersions["jooq"])
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
    configurations {
        create("Test") {
            generateSchemaSourceOnCompilation.set(true)
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc = null
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                        properties.add(Property().withKey("scripts").withValue("src/test/resources/database.sql"))
                        properties.add(Property().withKey("sort").withValue("semantic"))
                        properties.add(Property().withKey("unqualifiedSchema").withValue("none"))
                    }
                    strategy.apply {
                        name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    }
                    generate.apply {
                        withRelations(true)
                        withDeprecated(false)
                        withRecords(true)
                        withImmutablePojos(true)
                        withFluentSetters(true)
                    }
                    target.apply {
                        packageName = "io.github.zero88.rql.jooq"
                        directory = "generated/test/java"
                    }
                }
            }
        }
    }
}

task("jooqTest") {
    group = "jooq"
    dependsOn(project.getTasksByName("generateTestJooq", false))
}

tasks.compileTestJava {
    dependsOn(tasks["jooqTest"])
}

