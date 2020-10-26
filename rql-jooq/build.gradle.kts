plugins {
    id("nu.studer.jooq") version ("3.0.3")
}
val depVersions: Map<String, String> by ext

dependencies {
    api(project(":rql"))
    api("org.jooq:jooq:${depVersions["jooq"]}")
//    api("org.jooq:jooq-meta:${depVersions["slf4j"]")
//    api("org.jooq:jooq-meta-extensions:${depVersions["slf4j"]")
//    compileOnlyApi("com.fasterxml.jackson.core:jackson-databind:${depVersions["jackson"]}")
//    testImplementation("com.fasterxml.jackson.core:jackson-databind:${depVersions["jackson"]}")
}


