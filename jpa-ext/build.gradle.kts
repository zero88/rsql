val depVersions: Map<String, String> by ext

dependencies {
    compileOnlyApi("com.fasterxml.jackson.core:jackson-databind:${depVersions["jackson"]}")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:${depVersions["jackson"]}")
}


