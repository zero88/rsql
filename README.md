# Resource Query Language

![build](https://github.com/zero88/rsql/workflows/build/badge.svg?branch=master)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/zero88/rsql?include_prereleases&sort=semver)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/io.github.zero88/rsql-jooq?server=https%3A%2F%2Foss.sonatype.org)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.zero88/rsql-jooq?server=https%3A%2F%2Foss.sonatype.org)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=zero88_rsql&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=zero88_rsql)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=zero88_rsql&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=zero88_rsql)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=zero88_rsql&metric=security_rating)](https://sonarcloud.io/dashboard?id=zero88_rsql)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=zero88_rsql&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=zero88_rsql)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=zero88_rsql&metric=coverage)](https://sonarcloud.io/dashboard?id=zero88_rsql)

RESTful Service Query Language (RSQL) is a language, and a library designed for searching entries in RESTful services.

This library provides core functionality based on [rsql-parser](https://github.com/jirutka/rsql-parser) and make
extension for [jOOQ](https://www.jooq.org/), which is translated to `jOOQ DSL`.

## Usage

### With jOOQ

#### Parse to jOOQ Condition

The core functionality in `rsql-jooq` is creating `jOOQ condition` from RESTful query. For example:

```bash
> url
http://localhost:8080/api/data?q=(F_DATE=between=('2020-04-05T08:00:00','2020-04-08T08:00:00'))
> jooq
"ALL_DATA_TYPE"."F_DATE" between timestamp '2020-04-05 08:00:00.0' and timestamp '2020-04-08 08:00:00.0'

# With AND condition. Use [;] or [and]
> url
http://localhost:8080/api/data?q=(F_STR=='abc';F_BOOL=='true')

> jooq
( "ALL_DATA_TYPE"."F_STR" = 'abc' and "ALL_DATA_TYPE"."F_BOOL" = true )

# With OR condition. Use [,] or [or]
> url
http://localhost:8080/api/data?q=(F_DURATION=='abc',F_PERIOD=='xyz')
> jooq
( "ALL_DATA_TYPE"."F_DURATION" = 'abc' or "ALL_DATA_TYPE"."F_PERIOD" = 'xyz' )


# With combination AND and OR condition
> url
http://localhost:8080/api/data?q=(F_STR=='abc';F_BOOL=='true';(F_DURATION=='def',F_PERIOD=='xyz'))
> jooq
(
  "ALL_DATA_TYPE"."F_STR" = 'abc'
  and "ALL_DATA_TYPE"."F_BOOL" = true
  and (
    "ALL_DATA_TYPE"."F_DURATION" = 'def'
    or "ALL_DATA_TYPE"."F_PERIOD" = 'xyz'
  )
)
```

The entrypoint for the above magic is [JooqRqlParser](jooq/src/main/java/io/zero88/rsql/jooq/JooqRqlParser.java)

```java
String query=collectQueryPart(url);
    // With your table
    Condition condition=JooqRqlParser.DEFAULT.criteria(query,Tables.ALL_DATA_TYPE);
```

Currently, `rsql-jooq` supports these comparison nodes

| Name                  | Symbols              |
| --------------------- | -------------------- |
| EQUAL                 | [==]                 |
| NOT_EQUAL             | [!=]                 |
| GREATER_THAN          | [=gt=, >]            |
| GREATER_THAN_OR_EQUAL | [=ge=, >=]           |
| LESS_THAN             | [=lt=, <]            |
| LESS_THAN_OR_EQUAL    | [=le=, <=]           |
| IN                    | [=in=]               |
| NOT_IN                | [=out=]              |
| BETWEEN               | [=between=]          |
| EXISTS                | [=exists=, =nn=]     |
| NON_EXISTS            | [=null=, =isn=]      |
| NULLABLE              | [=nullable=]         |
| LIKE                  | [=like=]             |
| UNLIKE                | [=nk=, =unlike=]     |
| CONTAINS              | [=contains=]         |
| STARTS_WITH           | [=sw=, =startswith=] |
| ENDS_WITH             | [=ew=, =endswith=]   |


#### Customize comparison

Thanks to [ServiceLoader](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html), you can add more `comparison builder` by
extends [JooqComparisonCriteriaBuilder](/jooq/src/main/java/io/zero88/rsql/jooq/criteria/JooqComparisonCriteriaBuilder.java), then register in [META-INF/services/io.zero88.rsql.jooq.criteria.JooqComparisonCriteriaBuilder](jooq/src/main/resources/META-INF/services/io.zero88.rsql.jooq.criteria.JooqComparisonCriteriaBuilder)


For example:

```java
package your.project.pkg

public final class CustomOpBuilder extends JooqComparisonCriteriaBuilder {

    @Override
    public @NonNull ComparisonOperatorProxy operator() {
        return ComparisonOperatorProxy.create("=custom=");
    }

    @Override
    protected @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                         @NonNull JooqArgumentParser argParser,
                                         @NonNull LikeWildcardPattern wildcardPattern) {
        // do something here
        throw new UnsupportedOperationException("Not yet implemented")
    }

}
```

Create new resource file `META-INF/services/io.zero88.rsql.jooq.criteria.JooqComparisonCriteriaBuilder` in your resource folder, with all content in [default registry](jooq/src/main/resources/META-INF/services/io.zero88.rsql.jooq.criteria.JooqComparisonCriteriaBuilder) and appends your FQN class (e.g: `your.project.pkg.CustomOpBuilder`)

_Note_: in case that you don't support or overwrite any default comparison operator, it is safe to remove any line in service file.

#### Integrate with jOOQ Query

To make a life is easier, `rsql-jooq` provide some basic queries that can execute directly to achieve records. For
example:

```java
int count = JooqFetchCountQuery.builder()
                               .parser(jooqRqlParser)
                               .dsl(dsl)
                               .table(Tables.TABLES)
                               .build()
                               .execute(query)
                               .intValue()

boolean exists = JooqFetchExistQuery.builder()
                                    .parser(jooqRqlParser)
                                    .dsl(dsl)
                                    .table(Tables.TABLES)
                                    .build()
                                    .execute(query)
```

### RSQL syntax

RSQL syntax is described on [RSQL-parser's project page](https://github.com/jirutka/rsql-parser#grammar-and-semantic).

### Add to your project

To use `rsql` with `jooq` add the
following [dependency](https://search.maven.org/artifact/io.github.zero88/rsql-jooq/1.0.0/jar) to the dependencies
section of your build descriptor:

- `Maven` (in your `pom.xml`):

```xml

<dependency>
    <groupId>io.github.zero88</groupId>
    <artifactId>rsql-jooq</artifactId>
    <version>1.0.0-alpha1</version>
</dependency>
```

- `Gradle` (in your `build.gradle`):

```groovy
dependencies {
    api("io.github.zero88:rsql-jooq:1.0.0-alpha1")
}
```

**Hint**

`rsql-jooq` is only depended on 2 main libraries:

- `org.jooq:jooq`
- `org.slf4j:slf4j-api`

Then you need to add `jdbc driver` jar to your project.

#### Use core lib

Because, currently I'm busy with other project, so only one portable version for `jOOQ` was implemented.

To develop more portable lib to another database abstraction in Java such as `Hibernate`, `JPA`, `MyBatis`, you can use
only core module

- `Maven`

```xml

<dependency>
    <groupId>io.github.zero88</groupId>
    <artifactId>rsql-core</artifactId>
    <version>1.0.0-alpha1</version>
</dependency>
```

- `Gradle`

```groovy
dependencies {
    api("io.github.zero88:rsql-core:1.0.0-alpha1")
}
```

Then make extend in API core interface.
