val depVersions: Map<String, String> by ext

dependencies {
    api(project(":jpa-ext"))
    api("io.github.zero88:java-utils:1.0.0")
    api("org.slf4j:slf4j-api:${depVersions["slf4j"]}")
    api("cz.jirutka.rsql:rsql-parser:${depVersions["rsql"]}")
}
