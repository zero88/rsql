object UtilLibs {

    object Version {

        const val lombok = "1.18.16"
        const val jetbrainsAnnotations = "20.1.0"
    }

    const val lombok = "org.projectlombok:lombok:${Version.lombok}"
    const val jetbrainsAnnotations = "org.jetbrains:annotations:${Version.jetbrainsAnnotations}"
}

object PluginLibs {

    object Version {

        const val jooq = "5.2"
        const val nexusPublish = "1.1.0"
    }

    const val nexusPublish = "io.github.gradle-nexus.publish-plugin"
    const val jooq = "nu.studer.jooq"
}

object TestLibs {

    object Version {

        const val jsonassert = "1.5.0"
        const val junit5 = "5.7.0"
    }

    const val junit5Api = "org.junit.jupiter:junit-jupiter-api:${Version.junit5}"
    const val junit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Version.junit5}"
    const val junit5Vintage = "org.junit.vintage:junit-vintage-engine:${Version.junit5}"
    const val junit5Params = "org.junit.jupiter:junit-jupiter-params:${Version.junit5}"
    const val jsonassert = "org.skyscreamer:jsonassert:${Version.jsonassert}"

}

object TestContainers {
    object Version {

        const val ver = "1.15.2"
    }

    const val junit5 = "org.testcontainers:junit-jupiter:${Version.ver}"
    const val pgsql = "org.testcontainers:postgresql:${Version.ver}"
    const val mysql = "org.testcontainers:mysql:${Version.ver}"
}

object JacksonLibs {

    object Version {

        const val jackson = "2.12.0"
    }
    const val annotations = "com.fasterxml.jackson.core:jackson-annotations:${Version.jackson}"
    const val databind = "com.fasterxml.jackson.core:jackson-databind:${Version.jackson}"
}

object LogLibs {

    object Version {

        const val slf4j = "1.7.30"
        const val logback = "1.2.3"
    }

    const val slf4j = "org.slf4j:slf4j-api:${Version.slf4j}"
    const val logback = "ch.qos.logback:logback-classic:${Version.logback}"
}

object DatabaseLibs {

    object Version {

        //                const val jooq = "3.13.6"
        const val jooq = "3.14.8"
        const val h2 = "1.4.200"
        const val pgsql = "42.2.19"
        const val mysql = "8.0.23"
        const val hikari = "4.0.2"
        const val jpa = "2.2"
        const val jta = "1.3"
        const val agroal = "1.9"
        const val rsql = "2.1.0"
    }

    const val rsql = "cz.jirutka.rsql:rsql-parser:${Version.rsql}"
    const val h2 = "com.h2database:h2:${Version.h2}"
    const val pgsql = "org.postgresql:postgresql:${Version.pgsql}"
    const val mysql = "mysql:mysql-connector-java:${Version.mysql}"
    const val hikari = "com.zaxxer:HikariCP:${Version.hikari}"
    const val jpa = "javax.persistence:javax.persistence-api:${Version.jpa}"
    const val jta = "javax.transaction:javax.transaction-api:${Version.jta}"
    const val jooq = "org.jooq:jooq:${Version.jooq}"
    const val jooqMeta = "org.jooq:jooq-meta:${Version.jooq}"
    const val jooqMetaExt = "org.jooq:jooq-meta-extensions:${Version.jooq}"
    const val jooqCodegen = "org.jooq:jooq-codegen:${Version.jooq}"
    const val agroalApi = "io.agroal:agroal-api:${Version.agroal}"
    const val agroalPool = "io.agroal:agroal-pool:${Version.agroal}"
}

object ZeroLibs {
    object Version {

        const val utils = "2.0.0-SNAPSHOT"
        const val plugin = "2.0.0"
    }

    const val utils = "io.github.zero88:java-utils:${Version.utils}"

    object Plugins {

        const val oss = "io.github.zero88.gradle.oss"
        const val root = "io.github.zero88.gradle.root"
    }
}
