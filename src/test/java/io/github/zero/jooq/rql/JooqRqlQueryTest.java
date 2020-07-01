package io.github.zero.jooq.rql;

import java.util.UUID;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.meta.h2.information_schema.tables.Tables;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.zero.jooq.rql.query.JooqFetchCountQuery;
import io.github.zero.utils.Strings;

public class JooqRqlQueryTest {

    private JooqRqlParser jooqRqlParser;
    private DSLContext dsl;

    @Before
    public void before() {
        dsl = DSL.using("jdbc:h2:mem:dbh2mem-" + UUID.randomUUID().toString());
        jooqRqlParser = JooqRqlParser.DEFAULT;
    }

    @Test
    public void test_h2_schema_info() {
        final StringBuilder builder = new StringBuilder();
        final String query = builder.append(Tables.TABLE_SCHEMA.getName() + "==public")
                                    .append(";")
                                    .append(Tables.TABLE_NAME.getName() + "=exists=x")
                                    .append(" and ")
                                    .append("(")
                                    .append(Tables.TABLE_TYPE.getName() + "=in=(xyz,abc)")
                                    .append(",")
                                    .append(Tables.TABLE_CLASS.getName() + "=out=(123,456)")
                                    .append(")")
                                    .toString();
        final Condition condition = jooqRqlParser.criteria(query, Tables.TABLES);
        Assert.assertEquals("( 1 = 1 and \"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_SCHEMA\" = 'public' and " +
                            "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_NAME\" is not null and ( ( 1 = 1 and " +
                            "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_TYPE\" in ( 'xyz', 'abc' ) ) or " +
                            "\"INFORMATION_SCHEMA\".\"TABLES\".\"TABLE_CLASS\" not in ( '123', '456' ) ) )",
                            Strings.optimizeMultipleSpace(condition.toString()));
        Assert.assertEquals(0, JooqFetchCountQuery.builder()
                                                  .dsl(dsl)
                                                  .table(Tables.TABLES)
                                                  .build()
                                                  .execute(query)
                                                  .intValue());
    }

}