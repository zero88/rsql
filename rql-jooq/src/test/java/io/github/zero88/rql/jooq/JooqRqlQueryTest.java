package io.github.zero88.rql.jooq;

import java.util.UUID;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.meta.h2.information_schema.Tables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.zero88.rql.jooq.query.JooqFetchCountQuery;
import io.github.zero88.rql.jooq.query.JooqFetchExistQuery;
import io.github.zero88.utils.Strings;

public class JooqRqlQueryTest {

    private JooqRqlParser jooqRqlParser;
    private DSLContext dsl;

    @BeforeEach
    public void before() {
        dsl = DSL.using("jdbc:h2:mem:dbh2mem-" + UUID.randomUUID().toString());
        jooqRqlParser = JooqRqlParser.DEFAULT;
    }

    @Test
    public void test_h2_schema_info() {
        final String query = Tables.TABLES.TABLE_SCHEMA.getName() + "==public" + ";" +
                             Tables.TABLES.TABLE_NAME.getName() + "=exists=x" + " and " + "(" +
                             Tables.TABLES.TABLE_TYPE.getName() + "=in=(xyz,abc)" + "," +
                             Tables.TABLES.TABLE_CLASS.getName() + "=out=(123,456)" + ")";
        final Condition condition = jooqRqlParser.criteria(query, Tables.TABLES);
        Assertions.assertEquals("( true and \"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_SCHEMA\" = 'public' and " +
                                "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_NAME\" is not null and ( ( true and " +
                                "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_TYPE\" in ( 'xyz', 'abc' ) ) or " +
                                "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_CLASS\" not in ( '123', '456' ) ) )",
                                Strings.optimizeMultipleSpace(condition.toString()));
        Assertions.assertEquals(0, JooqFetchCountQuery.builder()
                                                      .dsl(dsl)
                                                      .table(Tables.TABLES)
                                                      .build()
                                                      .execute(query)
                                                      .intValue());
    }

    @Test
    public void test_h2_exist() {
        final String query = Tables.TABLES.TABLE_SCHEMA.getName() + "==public" + ";" +
                             Tables.TABLES.TABLE_NAME.getName() + "=exists=x" + " and " + "(" +
                             Tables.TABLES.TABLE_TYPE.getName() + "=in=(xyz,abc)" + "," +
                             Tables.TABLES.TABLE_CLASS.getName() + "=out=(123,456)" + ")";
        final Condition condition = jooqRqlParser.criteria(query, Tables.TABLES);
        Assertions.assertEquals("( true and \"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_SCHEMA\" = 'public' and " +
                                "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_NAME\" is not null and ( ( true and " +
                                "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_TYPE\" in ( 'xyz', 'abc' ) ) or " +
                                "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_CLASS\" not in ( '123', '456' ) ) )",
                                Strings.optimizeMultipleSpace(condition.toString()));
        Assertions.assertEquals(false,
                                JooqFetchExistQuery.builder().dsl(dsl).table(Tables.TABLES).build().execute(query));
    }

}